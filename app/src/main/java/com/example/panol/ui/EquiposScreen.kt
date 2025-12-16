package com.example.panol.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.panol.data.Equipo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreen(viewModel: MainViewModel) {
    val equipos by viewModel.equipos

    Scaffold(
        topBar = { TopAppBar(title = { Text("Inventario Disponible") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Navegar a crear reserva */ }) {
                Icon(Icons.Default.Add, contentDescription = "Reservar")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.padding(16.dp)) {
            items(equipos) { equipo ->
                EquipoCard(equipo)
            }
        }
    }
}

@Composable
fun EquipoCard(equipo: Equipo) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = equipo.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "${equipo.marca} - ${equipo.modelo}", style = MaterialTheme.typography.bodyMedium)
            }
            
            // Badge de Estado
            val color = when(equipo.estado) {
                "DISPONIBLE" -> Color.Green
                "MANTENCION" -> Color.Red
                else -> Color.Gray
            }
            
            Surface(
                color = color.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = equipo.estado,
                    color = color.copy(alpha = 1f), // Color más oscuro para texto
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}