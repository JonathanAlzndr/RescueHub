package com.example.rescuehub.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rescuehub.data.UserRepository
import com.example.rescuehub.di.Injection
import com.example.rescuehub.ui.home.HomeViewModel
import com.example.rescuehub.ui.login.LoginViewModel
import com.example.rescuehub.ui.main.MainViewModel
import com.example.rescuehub.ui.onboarding.OnboardingViewModel
import com.example.rescuehub.ui.profile.ProfileViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T
        }
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository) as T
        }
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository) as T
        }
        if(modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            return OnboardingViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}