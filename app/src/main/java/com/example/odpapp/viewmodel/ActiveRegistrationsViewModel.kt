package com.example.odpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.odpapp.data.model.ActiveRegistrationsRequest
import com.example.odpapp.data.model.RegistrationPlace
import com.example.odpapp.data.repository.ActiveRegistrationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActiveRegistrationsViewModel(
    private val repository: ActiveRegistrationsRepository
) : ViewModel() {

    private val _registrations = MutableStateFlow<List<RegistrationPlace>>(emptyList())
    val registrations: StateFlow<List<RegistrationPlace>> = _registrations.asStateFlow()

    // Dodajemo filtriranu listu
    private val _filteredRegistrations = MutableStateFlow<List<RegistrationPlace>>(emptyList())
    val filteredRegistrations: StateFlow<List<RegistrationPlace>> = _filteredRegistrations.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Parametri za request sa početnim vrijednostima
    private val _updateDate = MutableStateFlow("2025-06-01")
    val updateDate: StateFlow<String> = _updateDate.asStateFlow()

    private val _entityId = MutableStateFlow(1)
    val entityId: StateFlow<Int> = _entityId.asStateFlow()

    private val _cantonId = MutableStateFlow(1)
    val cantonId: StateFlow<Int> = _cantonId.asStateFlow()

    private val _municipalityId = MutableStateFlow(1)
    val municipalityId: StateFlow<Int> = _municipalityId.asStateFlow()

    init {
        fetchWithCurrentParams()
    }

    private fun fetchWithCurrentParams() {
        val request = ActiveRegistrationsRequest(
            updateDate = _updateDate.value,
            entityId = _entityId.value,
            cantonId = _cantonId.value,
            municipalityId = _municipalityId.value
        )
        fetchData(request)
    }

    private fun fetchData(request: ActiveRegistrationsRequest) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val data = repository.getActiveRegistrations(request)
                _registrations.value = data
                _filteredRegistrations.value = data
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // Funkcija za filtriranje prikaza po korisničkom upitu
    fun filterData(query: String) {
        _filteredRegistrations.value = if (query.isBlank()) {
            _registrations.value
        } else {
            _registrations.value.filter {
                it.registrationPlace.contains(query, ignoreCase = true)
            }
        }
    }

    // Funkcije za ažuriranje parametara i pozivanje fetcha
    fun setUpdateDate(date: String) {
        _updateDate.value = date
        fetchWithCurrentParams()
    }

    fun setEntityId(id: Int) {
        _entityId.value = id
        fetchWithCurrentParams()
    }

    fun setCantonId(id: Int) {
        _cantonId.value = id
        fetchWithCurrentParams()
    }

    fun setMunicipalityId(id: Int) {
        _municipalityId.value = id
        fetchWithCurrentParams()
    }

    class Factory(private val repository: ActiveRegistrationsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ActiveRegistrationsViewModel::class.java)) {
                return ActiveRegistrationsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
