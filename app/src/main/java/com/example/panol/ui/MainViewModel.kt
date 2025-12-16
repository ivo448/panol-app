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
    var userGroups = mutableStateOf<List<String>>(emptyList())
    var userPermissions = mutableStateOf<List<String>>(emptyList())

    fun login(user: String, pass: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = RetrofitClient.api.login(LoginRequest(user, pass))
                val tokenStr = "Token ${response.token}"
                token.value = tokenStr
                
                val userInfo = RetrofitClient.api.getMe(tokenStr)
                userPermissions.value = userInfo.permissions
                
                loginSuccess.value = true
                loadEquipos()
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun isStaff(): Boolean {
        // Verifica si tiene permisos de administrador o pañolero
        return userPermissions.value.any { it.contains("view_reserva") || it.contains("add_equipo") }
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