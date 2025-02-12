package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegistrarFichaje(navController: NavHostController, auth: FirebaseAuth) {
    val context = LocalContext.current
    val uid = auth.currentUser?.uid ?: ""

    // Estado para la fecha y hora actual
    val currentDateTime = remember { mutableStateOf(getCurrentDateTime()) }

    // Actualizar la fecha y hora cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Esperar 1 segundo
            currentDateTime.value = getCurrentDateTime() // Actualizar la fecha y hora
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registrar Fichaje", fontSize = 34.sp)

        Spacer(modifier = Modifier.height(8.dp))
        // Mostrar la fecha y hora actual
        Text(
            text = currentDateTime.value,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                registrarEntrada(uid,
                    onSuccess = {
                        Toast.makeText(context, "Entrada registrada", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { errorMsg: String ->
                        Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 15.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50), // Color de fondo (verde)
                contentColor = Color.White // Color del texto (blanco)
            )
        ) {
            Text(text = "Registrar Entrada", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                registrarSalida(uid,
                    onSuccess = {
                        Toast.makeText(context, "Salida registrada", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { errorMsg: String ->
                        Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 15.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4511E), // Color de fondo (naranja)
                contentColor = Color.White // Color del texto (blanco)
            )
        ) {
            Text(text = "Registrar Salida", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00838F), // Color de fondo (azul)
                contentColor = Color.White // Color del texto (blanco)
            )
        ) {
            Text(text = "Volver")
        }
    }
}

// Función para obtener la fecha y hora actual en formato legible
fun getCurrentDateTime(): String {
    val currentTime = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(currentTime))
}

// Registra la entrada para el usuario (crea un documento nuevo)
fun registrarEntrada(uid: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val registro = hashMapOf(
        "uid" to uid,
        "timestampEntrada" to FieldValue.serverTimestamp(),
        "timestampSalida" to null
    )
    db.collection("fichajes")
        .add(registro)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onFailure(e.message ?: "Error desconocido")
        }
}

// Registra la salida para el usuario (actualiza el último registro sin salida)
fun registrarSalida(uid: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    // Busca el último registro donde timestampSalida es null para el usuario
    db.collection("fichajes")
        .whereEqualTo("uid", uid)
        .whereEqualTo("timestampSalida", null)
        .orderBy("timestampEntrada", com.google.firebase.firestore.Query.Direction.ASCENDING)
        .limit(1)
        .get()
        .addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val doc = querySnapshot.documents[0]
                // Actualiza el registro con la hora de salida
                doc.reference.update("timestampSalida", com.google.firebase.firestore.FieldValue.serverTimestamp())
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onFailure(e.message ?: "Error al actualizar la salida") }
            } else {
                onFailure("No se encontró un registro de entrada activo")
            }
        }
        .addOnFailureListener { e ->
            onFailure(e.message ?: "Error al obtener registro activo")
        }
}