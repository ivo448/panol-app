package com.example.panol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panol.ui.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                NavHost(navController = navController, startDestination = "login") {
                    
                    composable("login") {
                        LoginScreen(viewModel) {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }

                    composable("home") {
                        EquiposScreen(viewModel, navController) 
                    }
                    
                    composable("reserva") {
                         Text("Pantalla de Reserva (En construcción)") // Placeholder temporal
                    }
                }
            }
        }
    }
}