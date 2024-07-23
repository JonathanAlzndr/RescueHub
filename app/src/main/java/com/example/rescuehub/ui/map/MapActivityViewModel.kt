package com.example.rescuehub.ui.map

import androidx.lifecycle.ViewModel
import com.example.rescuehub.data.UserRepository

class MapActivityViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDirection(origin: String, destination: String) = userRepository.getDirections(origin, destination)
}