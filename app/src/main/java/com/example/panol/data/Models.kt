package com.example.panolapp.data

// Modelo para el Login
data class LoginRequest(val username: String, val password: String)
data class AuthResponse(val token: String)

// Modelo de Equipo (Igual que tu Django Model)
data class Equipo(
    val id: Int,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val estado: String, // DISPONIBLE, MANTENCION, etc.
    val categoria_nombre: String?
)

// Modelo para Crear Reserva
data class ReservaRequest(
    val equipo: Int,
    val fecha_inicio: String, // Formato ISO 8601
    val fecha_fin: String
)