package com.example.odpapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.odpapp.ui.screens.ActiveRegistrationsScreen
import com.example.odpapp.ui.screens.OnboardingScreen
import com.example.odpapp.ui.screens.RegisteredVehiclesScreen
import com.example.odpapp.viewmodel.ActiveRegistrationsViewModel
import com.example.odpapp.viewmodel.RegisteredVehiclesViewModel
import com.example.odpapp.ui.screens.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val ACTIVE_REGISTRATIONS = "active_registrations"
    const val REGISTERED_VEHICLES = "registered_vehicles"
    // Dodaj ostale po potrebi
}

@Composable
fun AppNavGraph(
    activeRegistrationsViewModel: ActiveRegistrationsViewModel,
    registeredVehiclesViewModel: RegisteredVehiclesViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(onNavigateToDataScreen = { selectedMetrika ->
                when (selectedMetrika) {
                    "Broj aktivnih registracija" -> navController.navigate(Routes.ACTIVE_REGISTRATIONS)
                    "Registrovana vozila" -> navController.navigate(Routes.REGISTERED_VEHICLES)
                    // Dodaj ostale metrike i ekrane po potrebi
                }
            })
        }

        composable(Routes.ACTIVE_REGISTRATIONS) {
            ActiveRegistrationsScreen(viewModel = activeRegistrationsViewModel,navController = navController)
        }

        composable(Routes.REGISTERED_VEHICLES) {
            RegisteredVehiclesScreen(viewModel = registeredVehiclesViewModel,navController = navController)
        }
    }
}
