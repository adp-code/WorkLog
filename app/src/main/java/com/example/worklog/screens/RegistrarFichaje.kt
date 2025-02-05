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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistrarFichaje(navController: NavHostController, auth: FirebaseAuth) {
    val context = LocalContext.current
    val uid = auth.currentUser?.uid ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registrar Fichaje", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            registrarEntrada(uid,
                onSuccess = {
                    Toast.makeText(context, "Entrada registrada", Toast.LENGTH_SHORT).show()
                },
                onFailure = { errorMsg ->
                    Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            )
        }) {
            Text(text = "Registrar Entrada")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            registrarSalida(uid,
                onSuccess = {
                    Toast.makeText(context, "Salida registrada", Toast.LENGTH_SHORT).show()
                },
                onFailure = { errorMsg ->
                    Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            )
        }) {
            Text(text = "Registrar Salida")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
    }
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