package com.example.rescuehub.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.rescuehub.data.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getSession() = userRepository.getSession().asLiveData()
}