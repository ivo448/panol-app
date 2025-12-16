package com.example.panol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panol.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usamos Material Theme por defecto
            MaterialTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel() // ViewModel compartido

                NavHost(navController = navController, startDestination = "login") {
                    
                    // Pantalla de Login
                    composable("login") {
                        LoginScreen(viewModel) {
                            // Cuando el login es exitoso, vamos a Home
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true } // Borrar historial
                            }
                        }
                    }

                    // Pantalla Principal (Equipos)
                    composable("home") {
                        EquiposScreen(viewModel)
                    }
                }
            }
        }
    }
}