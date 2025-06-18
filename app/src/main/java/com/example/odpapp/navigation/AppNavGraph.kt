package com.example.odpapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.odpapp.ui.screens.*
import com.example.odpapp.ui.viewmodel.ActiveRegistrationsViewModel

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val ACTIVE_REGISTRATIONS_FILTER = "active_registrations_filter"
    const val ACTIVE_REGISTRATIONS_LIST = "active_registrations_list"
    const val ACTIVE_DETAILS = "active_details"
}

@Composable
fun AppNavGraph(
    activeRegistrationsViewModel: ActiveRegistrationsViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }


        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onNavigateToDataScreen = { selectedMetrika ->
                    when (selectedMetrika) {
                        "Broj aktivnih registracija" -> navController.navigate(Routes.ACTIVE_REGISTRATIONS_FILTER)
                        // Dodaj druge metrike ako treba
                    }
                }
            )
        }

        composable(Routes.ACTIVE_REGISTRATIONS_FILTER) {
            ActiveRegistrationsFilterScreen(
                viewModel = activeRegistrationsViewModel,
                onShowList = {
                    navController.navigate(Routes.ACTIVE_REGISTRATIONS_LIST)
                }
            )
        }

        composable(Routes.ACTIVE_REGISTRATIONS_LIST) {
            ActiveRegistrationsListScreen(
                viewModel = activeRegistrationsViewModel,
                onNavigateToDetails = { place ->
                    // Encode string param (registrationPlace) da bude sigurno u uri
                    val encodedPlace = java.net.URLEncoder.encode(place.registrationPlace, "UTF-8")
                    navController.navigate("${Routes.ACTIVE_DETAILS}?registrationPlace=$encodedPlace&total=${place.total}")
                }
            )
        }

        composable(
            route = "${Routes.ACTIVE_DETAILS}?registrationPlace={registrationPlace}&total={total}",
            arguments = listOf(
                navArgument("registrationPlace") { type = NavType.StringType; defaultValue = "" },
                navArgument("total") { type = NavType.IntType; defaultValue = 0 }
            )
        ) { backStackEntry ->
            val registrationPlace = backStackEntry.arguments?.getString("registrationPlace") ?: ""
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            RegistrationPlaceDetailsScreen(
                place = com.example.odpapp.data.model.RegistrationPlace(
                    registrationPlace = registrationPlace,
                    total = total
                )
            )
        }
    }
}
