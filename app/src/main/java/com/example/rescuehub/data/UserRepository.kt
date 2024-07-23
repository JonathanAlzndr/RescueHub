package com.example.rescuehub.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rescuehub.data.local.RescueCase
import com.example.rescuehub.data.local.SettingPreferences
import com.example.rescuehub.data.local.UserModel
import com.example.rescuehub.data.remote.api.ApiService
import com.example.rescuehub.data.remote.api.DirectionApiService
import com.example.rescuehub.data.remote.model.UserLogin
import com.example.rescuehub.data.remote.response.DirectionsResponse
import com.example.rescuehub.data.remote.response.LoginResponse
import com.example.rescuehub.utils.Result
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UserRepository private constructor(
    val apiService: ApiService,
    val pref: SettingPreferences,
    val directionApiService: DirectionApiService
) {

    fun login(username: String, password: String): LiveData<Result<LoginResponse>> {
        val user = UserLogin(username, password)
        val result = MutableLiveData<Result<LoginResponse>>()
        apiService.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(p0: Call<LoginResponse>, p1: Response<LoginResponse>) {
                if (p1.isSuccessful) {
                    if (p1.body() != null) {
                        result.value = Result.Success(p1.body()!!)
                    }
                } else {
                    result.value = Result.Error(p1.message())
                }
            }

            override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                Log.d(TAG, "onFailure: ${p1.printStackTrace()}")
                Result.Error(p1.message.toString())
            }
        })

        return result
    }

    suspend fun saveSession(user: UserModel) {
        pref.saveSession(user)
        Log.d(TAG, "saveSession: $user")
    }

    fun getSession(): Flow<UserModel> {
        val result = pref.getSession()
        Log.d(TAG, "getSession: $result")
        return result
    }

    suspend fun saveLaunchStatus(isFirstLaunch: Boolean) {
        pref.saveLaunchStatus(isFirstLaunch)
    }

    fun getThemeSetting(): Flow<Boolean> = pref.getThemeSetting()

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        pref.saveThemeSetting(isDarkMode)
    }

    suspend fun logout() {
        pref.logout()
    }

    fun getRescueCases(context: Context): List<RescueCase> {
        val jsonString = readJsonFromAssets(context, "data.json")
        jsonString?.let {
            val gson = Gson()
            return gson.fromJson(it, Array<RescueCase>::class.java).toList()
        } ?: return emptyList()
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }

    fun getDirections(origin: String, destination: String): LiveData<List<LatLng>> {
        val result = MutableLiveData<List<LatLng>>()
        directionApiService.getDirections(
            origin,
            destination,
            "AIzaSyBdVslfNQKJIhh9mjh46wjFFG19keKLL-4"
        ).enqueue(object: Callback<DirectionsResponse> {
            override fun onResponse(
                p0: Call<DirectionsResponse>,
                p1: Response<DirectionsResponse>
            ) {
                if(p1.isSuccessful) {
                    val polylineEncoded = p1.body()?.routes?.firstOrNull()?.overviewPolyline?.points
                    result.value = polylineEncoded?.let { decodePolyLine(it) }
                }
            }

            override fun onFailure(p0: Call<DirectionsResponse>, p1: Throwable) {
                Log.e(TAG, "onFailure: ${p1.message}", )
            }

        })

        return result
    }

    private fun decodePolyLine(encoded: String): List<LatLng> {
        val poly = mutableListOf<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) result or 0xffffff00.toInt() else result
            lat += dlat shr 1
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) result or 0xffffff00.toInt() else result
            lng += dlng shr 1
            poly.add(LatLng((lat / 1E5).toDouble(), (lng / 1E5).toDouble()))
        }
        return poly
    }


    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: SettingPreferences,
            directionApiService: DirectionApiService
        ): UserRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = UserRepository(apiService, pref, directionApiService)
                }
            }
            return INSTANCE as UserRepository
        }

        private const val TAG = "UserRepository"
    }
}