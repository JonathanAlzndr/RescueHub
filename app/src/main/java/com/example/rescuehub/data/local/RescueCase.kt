package com.example.rescuehub.data.local

data class RescueCase(
    val id: Int,
    val name: String,
    val date: String,
    val status: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String
)
