package com.example.odpapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.odpapp.ui.viewmodel.ActiveRegistrationsViewModel

@Composable
fun ActiveRegistrationsFilterScreen(
    viewModel: ActiveRegistrationsViewModel,
    onShowList: () -> Unit
) {
    // Ovdje možeš imati liste kantona i opština, za demo hardkodirano:
    val cantons = listOf(0 to "Sve", 1 to "Sarajevo", 2 to "Tuzla")
    val municipalities = listOf(0 to "Sve", 10 to "Centar", 11 to "Novi Grad")

    var expandedCanton by remember { mutableStateOf(false) }
    var expandedMunicipality by remember { mutableStateOf(false) }

    var selectedCanton by remember { mutableStateOf(cantons[0]) }
    var selectedMunicipality by remember { mutableStateOf(municipalities[0]) }
    var selectedDate by remember { mutableStateOf(viewModel.selectedUpdateDate) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Odaberi kanton:")
        ExposedDropdownMenuBox(expanded = expandedCanton, onExpandedChange = { expandedCanton = !expandedCanton }) {
            TextField(
                readOnly = true,
                value = selectedCanton.second,
                onValueChange = {},
                label = { Text("Kanton") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCanton) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedCanton,
                onDismissRequest = { expandedCanton = false }
            ) {
                cantons.forEach { canton ->
                    DropdownMenuItem(
                        text = { Text(canton.second) },
                        onClick = {
                            selectedCanton = canton
                            expandedCanton = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Text("Odaberi opštinu:")
        ExposedDropdownMenuBox(expanded = expandedMunicipality, onExpandedChange = { expandedMunicipality = !expandedMunicipality }) {
            TextField(
                readOnly = true,
                value = selectedMunicipality.second,
                onValueChange = {},
                label = { Text("Opština") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMunicipality) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedMunicipality,
                onDismissRequest = { expandedMunicipality = false }
            ) {
                municipalities.forEach { municipality ->
                    DropdownMenuItem(
                        text = { Text(municipality.second) },
                        onClick = {
                            selectedMunicipality = municipality
                            expandedMunicipality = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Datum može biti TextField ili DatePicker (za jednostavnost TextField)
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { selectedDate = it },
            label = { Text("Datum ažuriranja (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                // Postavi filter u viewmodel i pozovi fetch
                viewModel.selectedCantonId = selectedCanton.first
                viewModel.selectedMunicipalityId = selectedMunicipality.first
                viewModel.selectedUpdateDate = selectedDate
                viewModel.fetchActiveRegistrations()
                onShowList()
            }
        ) {
            Text("Prikaži aktivne registracije")
        }
    }
}
