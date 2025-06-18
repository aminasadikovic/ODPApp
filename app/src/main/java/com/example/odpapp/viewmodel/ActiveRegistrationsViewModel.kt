package com.example.odpapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odpapp.data.model.ActiveRegistrationsRequest
import com.example.odpapp.data.model.RegistrationPlace
import com.example.odpapp.data.repository.ActiveRegistrationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActiveRegistrationsViewModel(
    private val repository: ActiveRegistrationsRepository
): ViewModel() {

    private val _registrationPlaces = MutableStateFlow<List<RegistrationPlace>>(emptyList())
    val registrationPlaces: StateFlow<List<RegistrationPlace>> = _registrationPlaces

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Filter / konfiguracija
    var selectedCantonId = 0
    var selectedMunicipalityId = 0
    var selectedUpdateDate = "2023-06-29"

    // Filter za prikaz u listi (lokalni filter)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchActiveRegistrations() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val request = ActiveRegistrationsRequest(
                    updateDate = selectedUpdateDate,
                    entityId = 0,
                    cantonId = selectedCantonId,
                    municipalityId = selectedMunicipalityId
                )
                val data = repository.getActiveRegistrations(request)
                _registrationPlaces.value = data
            } catch (e: Exception) {
                _error.value = e.message ?: "Greška pri učitavanju podataka"
            }
            _loading.value = false
        }
    }

    // Primjena lokalnog filtera po nazivu registrationPlace
    val filteredList: StateFlow<List<RegistrationPlace>> =
        MutableStateFlow<List<RegistrationPlace>>(emptyList()).also { filteredFlow ->
            viewModelScope.launch {
                searchQuery.collect { query ->
                    val filtered = if (query.isBlank()) {
                        _registrationPlaces.value
                    } else {
                        _registrationPlaces.value.filter {
                            it.registrationPlace.contains(query, ignoreCase = true)
                        }
                    }
                    filteredFlow.value = filtered
                }
            }
        }
    companion object {
        class Factory(
            private val repository: ActiveRegistrationsRepository
        ) : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ActiveRegistrationsViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ActiveRegistrationsViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}
