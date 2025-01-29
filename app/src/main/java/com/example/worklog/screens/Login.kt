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

@Composable
fun Login(navController: NavHostController) {
    // Pantalla de Login
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Pantalla para el Login", fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("Home") }) {
            Text(text = "Ir a Home")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}