package com.example.odpapp.data.repository

import com.example.odpapp.data.api.ActiveRegistrationsApi
import com.example.odpapp.data.model.ActiveRegistrationsRequest
import com.example.odpapp.data.model.RegistrationPlace

class ActiveRegistrationsRepository(
    private val api: ActiveRegistrationsApi
) {
    suspend fun getActiveRegistrations(request: ActiveRegistrationsRequest): List<RegistrationPlace> {
        return api.getActiveRegistrations(request)

    }
}
