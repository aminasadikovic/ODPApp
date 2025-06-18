package com.example.odpapp.data.api

import com.example.odpapp.data.model.RegisteredVehicleRequest
import com.example.odpapp.data.model.RegisteredVehicleResponseItem
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisteredVehiclesApi {
    @POST("api/RegisteredVehiclesNumbers/list")
    suspend fun getRegisteredVehicles(
        @Body body: RegisteredVehicleRequest
    ): List<RegisteredVehicleResponseItem>
}