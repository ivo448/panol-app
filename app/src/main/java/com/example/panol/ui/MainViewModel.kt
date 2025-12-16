package com.example.panol.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panol.data.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    
    // Estados observables (como useState en React)
    var token = mutableStateOf("")
    var equipos = mutableStateOf<List<Equipo>>(emptyList())
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var loginSuccess = mutableStateOf(false)

    fun login(user: String, pass: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = RetrofitClient.api.login(LoginRequest(user, pass))
                token.value = "Token ${response.token}" // Guardamos "Token xxxx"
                loginSuccess.value = true
                loadEquipos() // Cargar datos automáticamente
            } catch (e: Exception) {
                errorMessage.value = "Error de Login: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadEquipos() {
        viewModelScope.launch {
            try {
                equipos.value = RetrofitClient.api.getEquipos(token.value)
            } catch (e: Exception) {
                errorMessage.value = "Error cargando equipos"
            }
        }
    }

    fun crearReserva(equipoId: Int, inicio: String, fin: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val req = ReservaRequest(equipoId, inicio, fin)
                RetrofitClient.api.crearReserva(token.value, req)
                onSuccess()
            } catch (e: Exception) {
                errorMessage.value = "Error al reservar: ${e.message}"
            }
        }
    }
}