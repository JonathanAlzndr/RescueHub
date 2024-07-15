package com.example.rescuehub.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rescuehub.data.local.SettingPreferences
import com.example.rescuehub.data.local.UserModel
import com.example.rescuehub.data.remote.api.ApiService
import com.example.rescuehub.data.remote.model.UserLogin
import com.example.rescuehub.data.remote.response.LoginResponse
import com.example.rescuehub.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(val apiService: ApiService, val pref: SettingPreferences) {

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

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService, pref: SettingPreferences): UserRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = UserRepository(apiService, pref)
                }
            }
            return INSTANCE as UserRepository
        }

        private const val TAG = "UserRepository"
    }
}