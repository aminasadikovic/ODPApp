package com.example.odpapp.data.repository

import com.example.odpapp.data.api.RegisteredVehiclesApi
import com.example.odpapp.data.model.RegisteredVehicleRequest
import com.example.odpapp.data.model.RegisteredVehicleResponseItem

class RegisteredVehiclesRepository(private val api: RegisteredVehiclesApi) {
    suspend fun getRegisteredVehicles(request: RegisteredVehicleRequest): List<RegisteredVehicleResponseItem> {
        return api.getRegisteredVehicles(request)
    }
}
