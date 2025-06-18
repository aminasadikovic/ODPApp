package com.example.odpapp.data.api

import com.example.odpapp.data.model.RegistrationPlace
import com.example.odpapp.data.model.ActiveRegistrationsRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ActiveRegistrationsApi {
    @POST("api/NumberOfActiveRegistrations/list")
    suspend fun getActiveRegistrations(
        @Body request: ActiveRegistrationsRequest
    ): List<RegistrationPlace>
}