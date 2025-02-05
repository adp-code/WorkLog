package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun EmpleadoEditar(navBackStackEntry: NavBackStackEntry, onEmpleadoActualizado: (() -> Unit)? = null) {
    // Recuperar el parámetro "nif"
    val nif = navBackStackEntry.arguments?.getString("nif") ?: "Desconocido"

    // Variables de estado para los campos del empleado
    val nombre = remember { mutableStateOf("") }
    val apellidos = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val departamento = remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    // Función para actualizar el empleado en Firestore
    fun actualizarEmpleado() {
        // Validación básica
        if (nombre.value.isBlank() || apellidos.value.isBlank() ||
            telefono.value.isBlank() || departamento.value.isBlank()
        ) {
            mensaje = "Todos los campos son obligatorios."
            return
        }

        cargando = true

        // Obtén la instancia de Firestore
        val db = FirebaseFirestore.getInstance()
        // Se asume que el documento se identifica con el nif
        val empleadoRef = db.collection("empleados").document(nif)

        // Datos a actualizar
        val datosActualizados = mapOf(
            "nombre" to nombre.value,
            "apellidos" to apellidos.value,
            "email" to email.value,
            "telefono" to telefono.value,
            "departamento" to departamento.value
        )

        // Ejecutamos la actualización en una corrutina
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Usa update para modificar únicamente los campos especificados
                empleadoRef.update(datosActualizados).await()
                // Si la operación fue exitosa, cambia el estado en el hilo principal
                kotlinx.coroutines.withContext(Dispatchers.Main) {
                    mensaje = "Empleado actualizado correctamente."
                    cargando = false
                    // Puedes ejecutar alguna acción, como volver a la pantalla anterior:
                    onEmpleadoActualizado?.invoke()
                }
            } catch (e: Exception) {
                // En caso de error, muestra un mensaje en el hilo principal
                kotlinx.coroutines.withContext(Dispatchers.Main) {
                    mensaje = "Error al actualizar: ${e.message}"
                    cargando = false
                }
            }
        }
    }

    // Interfaz de edición
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Editar Empleado", modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "NIF: $nif", modifier = Modifier.padding(bottom = 16.dp))

        // Campo para editar el nombre
        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo para editar los apellidos
        OutlinedTextField(
            value = apellidos.value,
            onValueChange = { apellidos.value = it },
            label = { Text("Apellidos") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo para editar el email
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo para editar el teléfono
        OutlinedTextField(
            value = telefono.value,
            onValueChange = { telefono.value = it },
            label = { Text("Teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo para editar el departamento
        OutlinedTextField(
            value = departamento.value,
            onValueChange = { departamento.value = it },
            label = { Text("Departamento") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Mensaje de estado (éxito o error)
        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, modifier = Modifier.padding(bottom = 8.dp))
        }

        // Botón para guardar los cambios
        Button(
            onClick = { actualizarEmpleado() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !cargando // Deshabilitar mientras se procesa la actualización
        ) {
            Text(text = if (cargando) "Guardando..." else "Guardar")
        }
    }
}