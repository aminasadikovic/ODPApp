package com.example.odpapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.odpapp.MainActivity
@Composable
fun OnboardingScreen(
    onNavigateToDataScreen: (String) -> Unit
) {
    val metrike = listOf(
        "Broj aktivnih registracija",
        "Registrovana vozila",
        "Registrovana vozila - fizička lica",
        "Broj zahtjeva za registraciju"
    )

    var selectedMetrika by remember { mutableStateOf(metrike[0]) } // Inicijalno selektovana prva metrika

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Dobrodošli u ODP Registracije!", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Odaberite tip podatka:")

        metrike.forEach { metrika ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp) // Dodao sam malo razmaka između redova radi bolje čitljivosti
            ) {
                RadioButton(
                    selected = selectedMetrika == metrika,
                    onClick = { selectedMetrika = metrika }
                )
                Spacer(modifier = Modifier.width(8.dp)) // Razmak između RadioButton i teksta
                Text(metrika)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onNavigateToDataScreen(selectedMetrika)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Započni")
        }
    }
}
