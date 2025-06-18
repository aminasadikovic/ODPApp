package com.example.odpapp.data.model

data class RegisteredVehicleRequest(
    val updateDate: String,
    val entityId: Int,
    val cantonId: Int,
    val municipalityId: Int,
    val year: String,
    val month: String
)
