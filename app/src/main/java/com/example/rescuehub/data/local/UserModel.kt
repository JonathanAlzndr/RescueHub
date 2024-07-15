package com.example.rescuehub.data.local

data class UserModel(
    val username: String,
    val isFirstLaunch: Boolean = true,
    val isLogin: Boolean = true
)
