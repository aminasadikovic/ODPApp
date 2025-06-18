package com.example.odpapp.data.model

data class ActiveRegistrationsRequest(
    val updateDate: String,
    val entityId: Int,
    val cantonId: Int,
    val municipalityId: Int
)