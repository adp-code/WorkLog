package com.example.worklog.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.worklog.models.Empleado
import com.example.worklog.navigation.AppScreens
import com.example.worklog.viewmodel.EmpleadosListarViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EmpleadosListar2(navController: NavHostController, auth: FirebaseAuth, ViewModel: EmpleadosListarViewModel) {

    //obtener la lista de empleados de empleadossListarViewModel


    var listaEmpleados by remember { mutableStateOf(emptyList<Empleado>()) }

    //incluimos la vista
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top= 60.dp)
    ) {
        Text(text = "Listado de Empleados",fontSize = 30.sp, fontWeight = FontWeight.Bold)

        //Realizar una operacion asincrona
        DisposableEffect(true) {
            val job = CoroutineScope(Dispatchers.IO).launch {
                val empleados = ViewModel.getEmpleadosViewModel()
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
                EmpleadoItem2(empleado = empleado, navController = navController)
            }
        }

    }
}

//Funcion para mostrar un item de la lista de empleados
@Composable
fun EmpleadoItem2(empleado: Empleado, navController: NavHostController) {

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
            Text(text = "Email: ${empleado.email}")
            Text(text = "Telefono: ${empleado.telefono}")
            Text(text = "Departamento: ${empleado.departamento}")

            Spacer(modifier = Modifier.height(8.dp))

            // Fila para los botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        // Navega a la pantalla de edición, pasando el nif del empleado
                        navController.navigate(AppScreens.EmpleadoEditar.createRoute(empleado.nif))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 5.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00838F), // Color de fondo (azul)
                        contentColor = Color.White // Color del texto (blanco)
                    )
                ) {
                    Text(text = "Editar", fontSize = 14.sp)

                }
                Button(
                    onClick = {
                        // Navega a la pantalla de eliminación, pasando el nif del empleado
                        navController.navigate(AppScreens.EmpleadoEliminar.createRoute(empleado.nif))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 5.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00838F), // Color de fondo (azul)
                        contentColor = Color.White // Color del texto (blanco)
                    )
                ) {
                    Text(text = "Borrar", fontSize = 14.sp)
                }
            }
        }
    }
}
