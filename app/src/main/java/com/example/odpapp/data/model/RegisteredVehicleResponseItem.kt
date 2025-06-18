package com.example.odpapp.data.model

data class RegisteredVehicleResponseItem(
    val registrationPlace: String,
    val totalDomestic: Int,
    val totalForeign: Int,
    val total: Int
)
