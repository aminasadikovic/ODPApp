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
import androidx.navigation.NavController
import com.example.odpapp.data.model.RegistrationPlace
import com.example.odpapp.viewmodel.ActiveRegistrationsViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun ActiveRegistrationsScreen(
    viewModel: ActiveRegistrationsViewModel,
    navController: NavController
) {
    val registrations by viewModel.filteredRegistrations.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    // Za ove parametre moraš u ViewModelu imati StateFlow-ove ili LiveData, pa ih ovako uzimamo:
    val updateDate by viewModel.updateDate.collectAsState()
    val entityId by viewModel.entityId.collectAsState()
    val cantonId by viewModel.cantonId.collectAsState()
    val municipalityId by viewModel.municipalityId.collectAsState()

    var updateDateText by remember { mutableStateOf(updateDate) }
    var entityIdText by remember { mutableStateOf(entityId.toString()) }
    var cantonIdText by remember { mutableStateOf(cantonId.toString()) }
    var municipalityIdText by remember { mutableStateOf(municipalityId.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Polje za unos updateDate
        OutlinedTextField(
            value = updateDateText,
            onValueChange = { updateDateText = it },
            label = { Text("Datum ažuriranja (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Polje za unos entityId
        OutlinedTextField(
            value = entityIdText,
            onValueChange = { entityIdText = it.filter { c -> c.isDigit() } },
            label = { Text("ID entiteta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Polje za unos cantonId
        OutlinedTextField(
            value = cantonIdText,
            onValueChange = { cantonIdText = it.filter { c -> c.isDigit() } },
            label = { Text("ID kantona") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Polje za unos municipalityId
        OutlinedTextField(
            value = municipalityIdText,
            onValueChange = { municipalityIdText = it.filter { c -> c.isDigit() } },
            label = { Text("ID općine") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (entityIdText.isNotEmpty() && cantonIdText.isNotEmpty() && municipalityIdText.isNotEmpty()) {
                    viewModel.setUpdateDate(updateDateText)
                    viewModel.setEntityId(entityIdText.toInt())
                    viewModel.setCantonId(cantonIdText.toInt())
                    viewModel.setMunicipalityId(municipalityIdText.toInt())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Učitaj podatke")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.filterData(it)
            },
            label = { Text("Pretraga po mjestu registracije") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Text("Greška: $error", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn {
                items(registrations) { reg ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("active_details/${reg.registrationPlace}/${reg.total}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Mjesto: ${reg.registrationPlace}", style = MaterialTheme.typography.titleMedium)
                            Text("Ukupno aktivnih: ${reg.total}")
                        }
                    }
                }
            }
        }
    }
}
