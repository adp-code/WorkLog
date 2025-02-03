package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegistrarFichaje(navController: NavHostController, auth: FirebaseAuth) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registrar Fichaje", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Aquí iría la lógica para registrar la hora de entrada en Firebase
        }) {
            Text(text = "Registrar Entrada")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            // Aquí iría la lógica para registrar la hora de salida en Firebase
        }) {
            Text(text = "Registrar Salida")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
    }
}