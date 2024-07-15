package com.example.rescuehub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rescuehub.data.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getSession() = userRepository.getSession().asLiveData()

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _isFirstLaunch = MutableLiveData<Boolean>()
    val isFirstLaunch: LiveData<Boolean> = _isFirstLaunch

    init {
        viewModelScope.launch {
            userRepository.getSession().collect { user ->
                _isLogin.postValue(user.isLogin)
                _isFirstLaunch.postValue(user.isFirstLaunch)
            }
        }
    }
}