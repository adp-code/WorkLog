package com.example.worklog.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.worklog.screens.*
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta) {
        composable(AppScreens.Login.ruta) { Login(navigationController, auth) }
        composable(AppScreens.Home.ruta) { Home(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadoAlta.ruta) {
            EmpleadoAlta(
                navigationController,
                auth,
                viewModel()
            )
        }
        composable(AppScreens.EmpleadosListar.ruta) {
            EmpleadosListar(
                navigationController,
                auth,
                viewModel()
            )
        }
        composable(AppScreens.EmpleadosListar2.ruta) {
            EmpleadosListar2(
                navigationController,
                auth,
                viewModel()
            )
        }
        composable(AppScreens.EmployeeHome.ruta) { EmployeeHome(navigationController, auth) }
        composable(AppScreens.RegistrarFichaje.ruta) {
            RegistrarFichaje(
                navigationController,
                auth
            )
        }
        composable(AppScreens.HistorialFichajes.ruta) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            HistorialFichajes(navigationController, auth, uid)
        }
        composable(AppScreens.EmployeeProfile.ruta) { EmployeeProfile(auth) }

        composable(
            route = AppScreens.EmpleadoEliminar.ruta,
            arguments = listOf(/* define argumentos si es necesario */)
        ) { backStackEntry ->
            EmpleadoEliminar(backStackEntry)
        }
        composable(
            route = AppScreens.EmpleadoEditar.ruta,
            arguments = listOf(/* define argumentos si es necesario */)
        ) { backStackEntry ->
            EmpleadoEditar(backStackEntry)
        }

        composable(
            route = "HistorialFichajes/{uid}",
            arguments = listOf(navArgument("uid") { nullable = true })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")
            val userUid = if (uid == "self") FirebaseAuth.getInstance().currentUser?.uid else uid
            HistorialFichajes(navigationController, FirebaseAuth.getInstance(), userUid)
        }
    }
}

fun Activity.setLocale(locale: Locale) {
    // Actualiza el Locale por defecto
    Locale.setDefault(locale)

    // Guarda el idioma seleccionado en SharedPreferences
    val sharedPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
    with(sharedPrefs.edit()) {
        putString("selected_language", locale.language)
        apply()
    }

    // Actualiza la configuración local del contexto actual
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    createConfigurationContext(config)

    // Recrea la actividad para que se apliquen los nuevos recursos
    recreate()
}