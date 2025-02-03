package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EmployeeHome(navController: NavHostController, auth: FirebaseAuth) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Menú del Empleado", fontSize = 24.sp)

        // Botón para registrar fichaje
        Button(onClick = { navController.navigate("RegistrarFichaje") }) {
            Text(text = "Registrar Fichaje")
        }

        // Botón para ver historial de fichajes
        Button(onClick = { navController.navigate("HistorialFichajes") }) {
            Text(text = "Ver Historial de Fichajes")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}