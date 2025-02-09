package com.example.worklog.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.worklog.R
import com.example.worklog.navigation.setLocale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

@Composable
fun Login(navController: NavHostController, auth: FirebaseAuth) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val fillFieldsMessage = stringResource(id = R.string.fill_fields)
    val errorUserMessage = stringResource(id = R.string.error_user)
    val employeeInfoNotFoundMessage = stringResource(id = R.string.employee_info_not_found)
    val errorEmployeeInfoMessage = stringResource(id = R.string.error_employee_info)
    val unknownRoleMessage = stringResource(id = R.string.unknown_role)
    val userNotFoundMessage = stringResource(id = R.string.user_not_found)
    val errorRoleMessage = stringResource(id = R.string.error_role)
    val loginFailedMessage = stringResource(id = R.string.login_failed)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.height(32.dp))

        // Etiqueta para el Email
        Text(
            text = stringResource(id = R.string.email),
            color = White,
            fontSize = 24.sp
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Etiqueta para la Contraseña
        Text(
            text = stringResource(id = R.string.password),
            color = White,
            fontSize = 24.sp
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            // Oculta los caracteres de la contraseña
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    context,
                    fillFieldsMessage,
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId == null) {
                            Toast.makeText(
                                context,
                                errorUserMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            return@addOnCompleteListener
                        }

                        val db = FirebaseFirestore.getInstance()
                        db.collection("usuarios").document(userId).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("role")
                                    when (role) {
                                        "admin" -> {
                                            navController.navigate("Home")
                                            Log.i("Worklog", "Usuario admin autenticado")
                                        }
                                        "empleado" -> {
                                            Log.i("WorklogEmpleado", "Usuario empleado autenticado")
                                            db.collection("empleados")
                                                .whereEqualTo("uid", userId)
                                                .get()
                                                .addOnSuccessListener { querySnapshot ->
                                                    if (!querySnapshot.isEmpty) {
                                                        val empleadoDoc = querySnapshot.documents[0]
                                                        val empleadoNombre = empleadoDoc.getString("nombre")
                                                        Log.i("WorklogEmpleado", "Empleado: $empleadoNombre")
                                                        navController.navigate("EmployeeHome")
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            employeeInfoNotFoundMessage,
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                                .addOnFailureListener { exception ->
                                                    Toast.makeText(
                                                        context,
                                                        "$errorEmployeeInfoMessage: ${exception.message}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                        }
                                        else -> {
                                            Toast.makeText(
                                                context,
                                                unknownRoleMessage,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        userNotFoundMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(
                                    context,
                                    "$errorRoleMessage: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        val errorMsg = task.exception?.message ?: "Error desconocido"
                        Toast.makeText(
                            context,
                            "$loginFailedMessage: $errorMsg",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("Worklog", errorMsg)
                    }
                }
        }) {
            Text(
                text = stringResource(id = R.string.login),
                color = White,
                fontSize = 18.sp
            )
        }
    }

    // Botón para cambiar el idioma en tiempo de ejecución
    val activity = LocalContext.current as? Activity
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                activity?.let {
                    val currentLang = Locale.getDefault().language
                    val newLocale = if (currentLang == "es") Locale("en") else Locale("es")
                    it.setLocale(newLocale)
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            val buttonText = if (Locale.getDefault().language == "es") "EN" else "ES"
            Text(text = buttonText)
        }

    }
}