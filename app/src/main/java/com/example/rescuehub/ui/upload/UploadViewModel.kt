package com.example.rescuehub.ui.upload

import androidx.lifecycle.ViewModel
import com.example.rescuehub.data.UserRepository
import java.io.File

class UploadViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun uploadImage(file: File, description: String) = userRepository.uploadImage(file, description)
}