package com.example.worklog.navigation

sealed class AppScreens (val ruta:String) {
    object Login: AppScreens("Login")
    object Home: AppScreens("Home")
    object EmpleadoAlta: AppScreens("EmpleadoAlta")
    object EmpleadoEliminar: AppScreens( "EmpleadoEliminar")
    object EmpleadosListar: AppScreens("EmpleadosListar")
    object EmpleadosListar2: AppScreens("EmpleadosListar2")


}