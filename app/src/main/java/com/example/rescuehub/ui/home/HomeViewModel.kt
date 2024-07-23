package com.example.rescuehub.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rescuehub.data.UserRepository
import com.example.rescuehub.data.local.RescueCase
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _rescueCases = MutableLiveData<List<RescueCase>>()
    val rescueCases: LiveData<List<RescueCase>> = _rescueCases

    fun getSession() = userRepository.getSession().asLiveData()

    fun loadRescueCases(context: Context) {
        viewModelScope.launch {
            val cases = userRepository.getRescueCases(context)
            _rescueCases.value = cases
        }
    }
}