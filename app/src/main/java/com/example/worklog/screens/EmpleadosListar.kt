package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.worklog.models.Empleado
import com.example.worklog.viewmodel.EmpleadosViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun EmpleadosListar(navController: NavHostController, auth: FirebaseAuth, ViewModel: EmpleadosViewModel) {

    var listaEmpleados by remember { mutableStateOf(emptyList<Empleado>()) }

    // Incluimos la vista
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        Text(text = "Listado de Empleados", fontWeight = FontWeight.Bold)

        // Realizamos una operación asíncrona para obtener la lista de empleados
        DisposableEffect(true) {
            val job = CoroutineScope(Dispatchers.IO).launch {
                val empleados = getEmpleados()
                listaEmpleados = empleados
            }
            onDispose { job.cancel() }
        }

        // LazyColumn para mostrar la lista de empleados
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(listaEmpleados) { empleado ->
                // Se pasa el navController para que el item pueda navegar
                EmpleadoItem(empleado = empleado, navController = navController)
            }
        }
    }
}

// Función suspendida para obtener la lista de empleados dentro de un try-catch
suspend fun getEmpleados(): List<Empleado> {
    return try {
        // Obtener instancia de Firebase Firestore
        val db = FirebaseFirestore.getInstance()

        // Almacenar el nombre de la colección
        val nombre_coleccion = "empleados"

        val querySnapshot = db.collection(nombre_coleccion).get().await()

        // Alternativamente, podrías procesar la lista de empleados, pero aquí
        // se retorna directamente el resultado convertido a objetos de tipo Empleado
        querySnapshot.toObjects(Empleado::class.java)
    } catch (e: Exception) {
        emptyList()
    }
}

// Función para mostrar un item de la lista de empleados, ahora con el navController
@Composable
fun EmpleadoItem(empleado: Empleado, navController: NavHostController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // Puedes usar fillMaxWidth() en lugar de fillMaxSize() para evitar que el contenido se estire verticalmente
                .padding(16.dp)
        ) {
            Text(text = "NIF: ${empleado.nif}")
            Text(text = "Nombre: ${empleado.nombre}")
            Text(text = "Apellidos: ${empleado.apellidos}")
            Text(text = "Email: ${empleado.email}")
            Text(text = "Teléfono: ${empleado.telefono}")
            Text(text = "Departamento: ${empleado.departamento}")

            Spacer(modifier = Modifier.height(8.dp))

            // Botón "Ver Fichaje"
            Button(
                onClick = {
                    navController.navigate("HistorialFichajes/${empleado.uid}")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ver Fichaje")
            }
        }
    }
}