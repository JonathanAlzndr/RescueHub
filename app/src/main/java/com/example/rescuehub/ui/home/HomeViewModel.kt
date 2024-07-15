package com.example.rescuehub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.rescuehub.data.UserRepository
import com.example.rescuehub.data.local.UserModel

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}