

package com.example.worklog.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worklog.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation (auth: FirebaseAuth) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta)
    {
        composable(AppScreens.Login.ruta) { Login(navigationController, auth) }
        composable(AppScreens.Home.ruta) { Home(navigationController, auth) }
        composable(AppScreens.EmpleadoAlta.ruta) { EmpleadoAlta(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadoEliminar.ruta) { EmpleadoEliminar(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadosListar.ruta) { EmpleadosListar(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadosListar2.ruta) { EmpleadosListar2(navigationController, auth, viewModel()) }
    }
}