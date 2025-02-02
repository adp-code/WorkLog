package com.example.worklog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.worklog.models.Empleado
import com.example.worklog.viewmodel.EmpleadosViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EmpleadosListar(navController: NavHostController, auth: FirebaseAuth, ViewModel: EmpleadosViewModel) {

    var listaEmpleados by remember { mutableStateOf(emptyList<Empleado>()) }

    //incluimos la vista
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top= 60.dp)
    ) {
        Text(text = "Listado de Empleados", fontWeight = FontWeight.Bold)

        //Realizar una operacion asincrona
        DisposableEffect(true) {
            val job = CoroutineScope(Dispatchers.IO).launch {
                val empleados = getEmpleados()

                listaEmpleados = empleados
            }
            onDispose {
                job.cancel()
            }
        }

        //LazyColumn para mostar la lista de empleados

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(listaEmpleados) { empleado ->
                EmpleadoItem(empleado)
            }
        }

    }

}

//Funcion suspendida para obtener la lista de empleados dentro de un try cath
suspend fun getEmpleados(): List<Empleado> {
    return try {
        // Obtener instancia de Firebase Firestore
        val db = FirebaseFirestore.getInstance()

        //Almacenar el nombre de la colección
        var nombre_coleccion = "empleados"

        val querySnapshot = db.collection(nombre_coleccion).get().await()

        val empleados = mutableListOf<Empleados>()

        for (document in querySnapshot.documents) {
            val empleado = document.toObject(Empleado::class.java)
            if (empleado != null) {
                empleados.add(empleado)
            }
        }
        querySnapshot.toObjects(Empleado::class.java)

    } catch (e: Exception) {
        emptyList()
    }
}

//Funcion para mostrar un item de la lista de empleados
@Composable
fun EmpleadoItem(empleado: Empleado) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "NIF: ${empleado.nif}")
            Text(text = "Nombre: ${empleado.nombre}")
            Text(text = "Apellidos: ${empleado.apellidos}")
            Text(text = "Telefono: ${empleado.telefono}")
            Text(text = "Departamento: ${empleado.departamento}")
        }
    }
}