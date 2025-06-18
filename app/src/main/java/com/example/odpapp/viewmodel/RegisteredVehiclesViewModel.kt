package com.example.odpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odpapp.data.model.RegisteredVehicleRequest
import com.example.odpapp.data.model.RegisteredVehicleResponseItem
import com.example.odpapp.data.repository.RegisteredVehiclesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider

class RegisteredVehiclesViewModel(private val repository: RegisteredVehiclesRepository) : ViewModel() {

    private val _vehicles = MutableStateFlow<List<RegisteredVehicleResponseItem>>(emptyList())
    val vehicles: StateFlow<List<RegisteredVehicleResponseItem>> = _vehicles

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadVehicles(request: RegisteredVehicleRequest) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = repository.getRegisteredVehicles(request)
                _vehicles.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
    class Factory(private val repository: RegisteredVehiclesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisteredVehiclesViewModel::class.java)) {
                return RegisteredVehiclesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
