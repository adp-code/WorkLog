

package com.example.worklog.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.worklog.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation (auth: FirebaseAuth) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta)
    {
        composable(AppScreens.Login.ruta) { Login(navigationController, auth) }
        composable(AppScreens.Home.ruta) { Home(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadoAlta.ruta) { EmpleadoAlta(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadosListar.ruta) { EmpleadosListar(navigationController, auth, viewModel()) }
        composable(AppScreens.EmpleadosListar2.ruta) { EmpleadosListar2(navigationController, auth, viewModel()) }
        composable(AppScreens.EmployeeHome.ruta) { EmployeeHome(navigationController, auth) } // Nueva ruta
        composable(AppScreens.RegistrarFichaje.ruta) { RegistrarFichaje(navigationController, auth) } // Nueva ruta
        composable(AppScreens.HistorialFichajes.ruta) { HistorialFichajes(navigationController, auth) } // Nueva ruta

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

        // composable(
        //    route = AppScreens.EmpleadoFichaje.ruta,
        //    arguments = listOf(navArgument("nif") { type = NavType.StringType })
        // ) { backStackEntry ->
        //    val nif = backStackEntry.arguments?.getString("nif") ?: ""
        //    FichajeScreen(nif = nif)
        //}
    }
}