package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun EmpleadoEliminar(
    navBackStackEntry: NavBackStackEntry,
    onEmpleadoEliminado: (() -> Unit)? = null
) {
    // Recupera el parámetro "nif" de la ruta
    val nif = navBackStackEntry.arguments?.getString("nif") ?: "Sin NIF"

    // Estados para el manejo de mensajes y carga
    var mensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    // Función para eliminar el empleado de Firebase
    fun eliminarEmpleado() {
        cargando = true
        val db = FirebaseFirestore.getInstance()
        // Se asume que el documento se identifica por el 'nif'
        val empleadoRef = db.collection("empleados").document(nif)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                empleadoRef.delete().await()
                withContext(Dispatchers.Main) {
                    mensaje = "Empleado eliminado correctamente."
                    cargando = false
                    // Llamar a la función callback si se proporcionó (por ejemplo, para navegar a otra pantalla)
                    onEmpleadoEliminado?.invoke()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    mensaje = "Error al eliminar: ${e.message}"
                    cargando = false
                }
            }
        }
    }

    // Interfaz de usuario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "¿Estás seguro de eliminar el empleado con NIF: $nif?")
        Spacer(modifier = Modifier.height(16.dp))
        if (mensaje.isNotEmpty()) {
            Text(text = mensaje)
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Botón para confirmar la eliminación
        Button(
            onClick = { eliminarEmpleado() },
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (cargando) "Eliminando..." else "Eliminar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Botón para cancelar o volver (opcional)
        OutlinedButton(
            onClick = { onEmpleadoEliminado?.invoke() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cancelar")
        }
    }
}