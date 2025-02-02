package com.example.worklog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.worklog.viewmodel.EmpleadosViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home (navController: NavHostController, auth: FirebaseAuth, ViewModel: EmpleadosViewModel) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Menú de la App", fontSize = 24.sp)

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("EmpleadoAlta") }) {
            Text(text = "Alta de Empleado")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("EmpleadoELiminar") }) {
            Text(text = "Eliminar Empleado")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("EmpleadosListar") }) {
            Text(text = "Listar Empleados")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("EmpleadosListar2") }) {
            Text(text = "Listar Empleados 2")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}