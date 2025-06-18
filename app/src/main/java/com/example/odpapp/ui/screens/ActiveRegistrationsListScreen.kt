package com.example.odpapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.odpapp.data.model.RegistrationPlace
import com.example.odpapp.ui.viewmodel.ActiveRegistrationsViewModel

@Composable
fun ActiveRegistrationsListScreen(
    viewModel: ActiveRegistrationsViewModel,
    onNavigateToDetails: (RegistrationPlace) -> Unit
) {
    val registrationPlaces by viewModel.registrationPlaces.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            label = { Text("Pretraži po mjestu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (error != null) {
            Text("Greška: $error", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(registrationPlaces) { place ->
                    RegistrationPlaceListItem(place, onClick = { onNavigateToDetails(place) })
                }
            }
        }
    }
}

@Composable
fun RegistrationPlaceListItem(
    place: RegistrationPlace,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = place.registrationPlace, style = MaterialTheme.typography.bodyLarge)
            Text(text = place.total.toString(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
