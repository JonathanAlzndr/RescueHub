package com.example.rescuehub.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuehub.data.UserRepository
import com.example.rescuehub.data.local.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun login(username: String, password: String) = userRepository.login(username, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

}