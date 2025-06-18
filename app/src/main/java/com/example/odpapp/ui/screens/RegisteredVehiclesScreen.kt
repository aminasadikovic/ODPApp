package com.example.odpapp.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.odpapp.viewmodel.RegisteredVehiclesViewModel
import androidx.navigation.NavController


@Composable
fun RegisteredVehiclesScreen(viewModel: RegisteredVehiclesViewModel,navController: NavController) {
    val vehicles by viewModel.vehicles.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // UI:
    if (loading) {
        CircularProgressIndicator()
    } else if (error != null) {
        Text("Greška: $error")
    } else {
        LazyColumn {
            items(vehicles) { vehicle ->
                Text("${vehicle.registrationPlace}: domaća vozila ${vehicle.totalDomestic}, strana vozila ${vehicle.totalForeign}, ukupno ${vehicle.total}")
            }
        }
    }
}
