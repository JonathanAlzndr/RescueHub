package com.example.rescuehub.di

import android.content.Context
import com.example.rescuehub.data.UserRepository
import com.example.rescuehub.data.local.SettingPreferences
import com.example.rescuehub.data.local.dataStore
import com.example.rescuehub.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context) : UserRepository{
        val apiService = ApiConfig.getApiService()
        val pref = context.dataStore
        val settingPreferences = SettingPreferences.getInstance(pref)
        return UserRepository.getInstance(apiService, settingPreferences)
    }
}