package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worklog.models.Empleado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EmployeeProfile(auth: FirebaseAuth) {
    val userId = auth.currentUser?.uid
    var empleado by remember { mutableStateOf<Empleado?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // Se lanza la consulta solo cuando se obtiene el uid
    LaunchedEffect(userId) {
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("empleados")
                .whereEqualTo("uid", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        // Se asume que existe un único documento para el empleado
                        empleado = querySnapshot.documents[0].toObject(Empleado::class.java)
                    } else {
                        errorMessage = "No se encontró la información del empleado"
                    }
                }
                .addOnFailureListener { exception ->
                    errorMessage = "Error: ${exception.message}"
                }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 28.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE0F7FA) // Color de fondo personalizado (cian claro)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .padding(start = 10.dp)
                .padding(end = 10.dp)

        ) {

            Text(
                text = "Mis Datos", fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )


            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                ) {
                Column {
                    if (errorMessage.isNotEmpty()) {
                        Text(text = errorMessage, fontSize = 18.sp)
                    } else if (empleado == null) {
                        Text(text = "Cargando...", fontSize = 18.sp)
                    } else {
                        Text(text = "Nombre: ${empleado?.nombre}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Apellidos: ${empleado?.apellidos}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Email: ${empleado?.email}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Teléfono: ${empleado?.telefono}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Departamento: ${empleado?.departamento}", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}