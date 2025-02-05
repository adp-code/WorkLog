package com.example.worklog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.worklog.viewmodel.EmpleadosViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EmpleadoAlta(navController: NavHostController, auth: FirebaseAuth, ViewModel: EmpleadosViewModel) {

    val db = FirebaseFirestore.getInstance()

    var nombre_coleccion = "empleados"

    val nif:String by ViewModel.nif.observeAsState(initial = "")
    val nombre:String by ViewModel.nombre.observeAsState (initial = "")
    val apellidos:String by ViewModel.apellidos.observeAsState (initial = "")
    val telefono:String by ViewModel.telefono.observeAsState (initial = "")
    val departamento:String by ViewModel.departamento.observeAsState (initial = "")
    val email:String by ViewModel.email.observeAsState (initial = "")
    val uid:String by ViewModel.uid.observeAsState (initial = "")

    val isButtonEnable:Boolean by ViewModel.isButtonEnable.observeAsState (initial = false)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.small

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .padding(start = 10.dp)
                .padding(end = 10.dp)

        ) {

            Text(
                text = "Alta de Empleado",
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = nif,
                onValueChange = { ViewModel.onCompletedFields(nif = it, nombre = nombre, apellidos = apellidos, telefono = telefono, departamento = departamento, email = email, uid = uid) },
                label = { Text("Introduce el NIF") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { ViewModel.onCompletedFields(nif = nif, nombre = it, apellidos = apellidos, telefono = telefono, departamento = departamento, email = email, uid = uid) },
                label = { Text("Introduce el nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { ViewModel.onCompletedFields(nif = nif, nombre = nombre, apellidos = it, telefono = telefono, departamento = departamento, email = email, uid = uid)},
                label = { Text("Introduce los apellidos") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { ViewModel.onCompletedFields(nif = nif, nombre = nombre, apellidos = apellidos, telefono = telefono, departamento = departamento, email = it, uid = uid)},
                label = { Text("Introduce el email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { ViewModel.onCompletedFields(nif = nif, nombre = nombre, apellidos = apellidos, telefono = it, departamento = departamento, email = email, uid = uid)},
                label = { Text("Introduce el telefono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.size(5.dp))

            OutlinedTextField(
                value = departamento,
                onValueChange = { ViewModel.onCompletedFields(nif = nif, nombre = nombre, apellidos = apellidos, telefono = telefono, departamento = it, email = email, uid = uid)},
                label = { Text("Introduce el departamento") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.size(5.dp))

            val dato = hashMapOf(
                "nif" to nif.toString(),
                "nombre" to nombre.toString(),
                "apellidos" to apellidos.toString(),
                "email" to email.toString(),
                "telefono" to telefono.toString(),
                "departamento" to departamento.toString(),
                "uid" to uid.toString()

            )

            var mensaje_confirmacion by remember { mutableStateOf("") }

            Button(

                onClick = {
                    db.collection(nombre_coleccion)
                        .document(nif)
                        .set(dato)
                        .addOnSuccessListener {
                            mensaje_confirmacion ="Datos guardados correctamente"


                        }
                        .addOnFailureListener {
                            mensaje_confirmacion ="No se ha podido guardar"

                        }
                },

                // EJEMPLO DE VIEWMODEL PARA HABILITAR EL BOTÓN
                enabled= isButtonEnable,

                border = BorderStroke(1.dp, Color.Black)
            )
            {

                Text(text = "Guardar")

            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = mensaje_confirmacion)
        }
    }
}