package com.example.rescuehub.data.remote.api

import com.example.rescuehub.data.remote.model.UserLogin
import com.example.rescuehub.data.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body user: UserLogin) : Call<LoginResponse>
}