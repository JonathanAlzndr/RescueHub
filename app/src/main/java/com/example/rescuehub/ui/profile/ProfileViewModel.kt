package com.example.rescuehub.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuehub.data.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}