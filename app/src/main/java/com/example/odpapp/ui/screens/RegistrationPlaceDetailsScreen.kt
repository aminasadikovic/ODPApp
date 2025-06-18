package com.example.odpapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.odpapp.data.model.RegistrationPlace

@Composable
fun RegistrationPlaceDetailsScreen(
    place: RegistrationPlace
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detalji registracije", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Mjesto registracije: ${place.registrationPlace}", style = MaterialTheme.typography.bodyLarge)
        Text("Ukupno aktivnih registracija: ${place.total}", style = MaterialTheme.typography.bodyLarge)
    }
}
