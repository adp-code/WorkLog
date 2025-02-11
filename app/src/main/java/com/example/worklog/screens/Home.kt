package com.example.worklog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.worklog.viewmodel.EmpleadosViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home (navController: NavHostController, auth: FirebaseAuth, ViewModel:EmpleadosViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "Menú de la App", fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.weight(0.8f))

        Button(onClick = { navController.navigate("EmpleadoAlta") },
            modifier = Modifier
                .height(120.dp) // Aumentar la altura del botón
                .fillMaxWidth(0.8f), // Ancho del 80% de la pantalla
            shape = RoundedCornerShape(8.dp), // Esquinas menos redondeadas (más cuadradas)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul oscuro)
                contentColor = Color.White // Color del texto (blanco)
            ) ) {
            Text(text = "Alta de Empleado", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.weight(0.8f))
        Button(onClick = { navController.navigate("EmpleadosListar") },
            modifier = Modifier
                .height(120.dp) // Aumentar la altura del botón
                .fillMaxWidth(0.8f), // Ancho del 80% de la pantalla
            shape = RoundedCornerShape(8.dp), // Esquinas menos redondeadas (más cuadradas)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul oscuro)
                contentColor = Color.White // Color del texto (blanco)
            ) ) {
            Text(text = "Lista de Empleados", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.weight(0.8f))
        Button(onClick = { navController.navigate("EmpleadosListar2") },
            modifier = Modifier
                .height(120.dp) // Aumentar la altura del botón
                .fillMaxWidth(0.8f), // Ancho del 80% de la pantalla
            shape = RoundedCornerShape(8.dp), // Esquinas menos redondeadas (más cuadradas)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul oscuro)
                contentColor = Color.White // Color del texto (blanco)
            ) ) {
            Text(text = "Editar/Borrar Empleados", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}