package com.example.panol.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PanolApi {
    @POST("api-token-auth/")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("api/users/me/")
    suspend fun getMe(@Header("Authorization") token: String): UserInfo

    @GET("api/equipos/")
    suspend fun getEquipos(@Header("Authorization") token: String): List<Equipo>

    @POST("api/reservas/")
    suspend fun crearReserva(
        @Header("Authorization") token: String,
        @Body reserva: ReservaRequest
    ): Any 
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/" 

    val api: PanolApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PanolApi::class.java)
    }
}