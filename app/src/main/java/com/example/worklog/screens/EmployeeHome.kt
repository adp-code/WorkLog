package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

        Text(text = "Perfil del Empleado", fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.weight(0.8f))

        // Botón para ir al perfil
        Button(onClick = { navController.navigate("EmployeeProfile") },
            modifier = Modifier
                .height(100.dp) // Aumentar la altura del botón
                .fillMaxWidth(0.8f), // Ancho del 80% de la pantalla
            shape = RoundedCornerShape(8.dp), // Esquinas menos redondeadas (más cuadradas)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul oscuro)
                contentColor = Color.White // Color del texto (blanco)
            ) ) {
            Text(text = "Mi Perfil", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.weight(0.8f))

        // Botón para registrar fichaje
        Button(onClick = { navController.navigate("RegistrarFichaje") },
            modifier = Modifier
                .height(100.dp) // Aumentar la altura del botón
                .fillMaxWidth(0.8f), // Ancho del 80% de la pantalla
            shape = RoundedCornerShape(8.dp), // Esquinas menos redondeadas (más cuadradas)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul oscuro)
                contentColor = Color.White // Color del texto (blanco)
            ) ) {
            Text(text = "Registrar Fichaje", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.weight(0.8f))

        // Botón para ver historial de fichajes
        Button(onClick = { navController.navigate("HistorialFichajes/self") },
            modifier = Modifier
                .height(100.dp) // Aumentar la altura del botón
                .fillMaxWidth(0.8f), // Ancho del 80% de la pantalla
            shape = RoundedCornerShape(8.dp), // Esquinas menos redondeadas (más cuadradas)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul oscuro)
                contentColor = Color.White // Color del texto (blanco)
            ) ) {
            Text(text = "Ver Historial de Fichajes", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.weight(0.8f))
    }
}