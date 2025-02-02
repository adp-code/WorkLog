package com.example.worklog.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.worklog.viewmodel.EmpleadosViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EmpleadoEliminar (NavController: NavHostController, auth: FirebaseAuth, ViewModel: EmpleadosViewModel) {
    Text(text ="Empleados Eliminar")





}