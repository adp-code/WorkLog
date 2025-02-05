package com.example.worklog.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

data class Fichaje(
    val timestampEntrada: Timestamp? = null,
    val timestampSalida: Timestamp? = null
)

@Composable
fun HistorialFichajes(navController: NavHostController, auth: FirebaseAuth, uidFromNav: String?) {
    val uid = uidFromNav ?: auth.currentUser?.uid ?: ""
    var fichajes by remember { mutableStateOf<List<Fichaje>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    // Consulta los fichajes para el usuario específico
    LaunchedEffect(uid) {
        if (uid.isNotEmpty()) {
            val db = FirebaseFirestore.getInstance()
            db.collection("fichajes")
                .whereEqualTo("uid", uid)
                .orderBy("timestampEntrada")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    fichajes = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(Fichaje::class.java)
                    }
                    Log.d("HistorialFichajes", "Fichajes encontrados: ${fichajes.size}")
                    loading = false
                }
                .addOnFailureListener { e ->
                    Log.e("HistorialFichajes", "Error al obtener fichajes: ${e.message}")
                    loading = false
                }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Historial de Fichajes", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            Text(text = "No hay registros disponibles")
        } else if (fichajes.isEmpty()) {
            Text(text = "No hay fichajes disponibles")
        } else {
            LazyColumn {
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Fecha", modifier = Modifier.weight(1f))
                        Text(text = "Entrada", modifier = Modifier.weight(1f))
                        Text(text = "Salida", modifier = Modifier.weight(1f))
                        Text(text = "Total Hrs", modifier = Modifier.weight(1f))
                    }
                }
                items(fichajes) { fichaje ->
                    FichajeItem(fichaje)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
    }
}

@Composable
fun FichajeItem(fichaje: Fichaje) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val fecha = fichaje.timestampEntrada?.toDate()?.let { dateFormat.format(it) } ?: "-"
    val entrada = fichaje.timestampEntrada?.toDate()?.let { timeFormat.format(it) } ?: "-"
    val salida = fichaje.timestampSalida?.toDate()?.let { timeFormat.format(it) } ?: "-"

    // Calcula el total de horas si timestampSalida existe
    val totalHoras: String = if (fichaje.timestampEntrada != null && fichaje.timestampSalida != null) {
        val diffMillis = fichaje.timestampSalida!!.toDate().time - fichaje.timestampEntrada!!.toDate().time
        // Convertir milisegundos a horas, redondeado a dos decimales
        val hours = diffMillis / 3600000.0
        String.format("%.2f", hours)
    } else {
        "-"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = fecha, modifier = Modifier.weight(1f))
        Text(text = entrada, modifier = Modifier.weight(1f))
        Text(text = salida, modifier = Modifier.weight(1f))
        Text(text = totalHoras, modifier = Modifier.weight(1f))
    }
}