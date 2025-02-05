package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, fontSize = 18.sp)
        } else if (empleado == null) {
            Text(text = "Cargando...", fontSize = 18.sp)
        } else {
            Text(text = "Nombre: ${empleado?.nombre}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Apellidos: ${empleado?.apellidos}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: ${empleado?.email}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Teléfono: ${empleado?.telefono}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Departamento: ${empleado?.departamento}", fontSize = 20.sp)
        }
    }
}