package com.example.rescuehub.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuehub.data.UserRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun saveLaunchStatus(isFirstLaunch: Boolean) {
        viewModelScope.launch {
            userRepository.saveLaunchStatus(isFirstLaunch)
        }
    }
}