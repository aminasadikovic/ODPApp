package com.example.odpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.odpapp.data.api.RetrofitInstance
import com.example.odpapp.data.repository.ActiveRegistrationsRepository
import com.example.odpapp.data.repository.RegisteredVehiclesRepository
import com.example.odpapp.navigation.AppNavGraph
import com.example.odpapp.ui.theme.ODPAppTheme
import com.example.odpapp.ui.viewmodel.ActiveRegistrationsViewModel
import com.example.odpapp.viewmodel.RegisteredVehiclesViewModel

class MainActivity : ComponentActivity() {
    private val activeRegistrationsRepository = ActiveRegistrationsRepository(RetrofitInstance.activeRegistrationsApi)
    private val registeredVehiclesRepository = RegisteredVehiclesRepository(RetrofitInstance.registeredVehiclesApi)

    private val activeRegistrationsViewModel: ActiveRegistrationsViewModel by viewModels {
        ActiveRegistrationsViewModel.Companion.Factory(activeRegistrationsRepository)
    }
    private val registeredVehiclesViewModel: RegisteredVehiclesViewModel by viewModels {
        RegisteredVehiclesViewModel.Factory(registeredVehiclesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ODPAppTheme {
                AppNavGraph(
                    activeRegistrationsViewModel = activeRegistrationsViewModel
                )
            }
        }
    }
}
