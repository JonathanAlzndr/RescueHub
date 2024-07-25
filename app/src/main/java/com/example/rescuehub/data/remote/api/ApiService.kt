package com.example.rescuehub.data.remote.api

import com.example.rescuehub.data.remote.model.UserLogin
import com.example.rescuehub.data.remote.response.FileUploadResponse
import com.example.rescuehub.data.remote.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("login")
    fun login(@Body user: UserLogin) : Call<LoginResponse>

    @Multipart
    @POST("stories/guest")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse
}