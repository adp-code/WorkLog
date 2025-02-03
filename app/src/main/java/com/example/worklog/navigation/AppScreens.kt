package com.example.worklog.navigation

sealed class AppScreens (val ruta:String) {
    object Login: AppScreens("Login")
    object Home: AppScreens("Home")
    object EmpleadoAlta: AppScreens("EmpleadoAlta")
    //object EmpleadoEliminar: AppScreens( "EmpleadoEliminar")
    //object EmpleadoEditar: AppScreens("EmpleadoEditar")
    object EmpleadosListar: AppScreens("EmpleadosListar")
    object EmpleadosListar2: AppScreens("EmpleadosListar2")

    object EmpleadoEditar: AppScreens("EmpleadoEditar/{nif}") {
        fun createRoute(nif: String) = "EmpleadoEditar/$nif"
    }
    object EmpleadoEliminar: AppScreens("EmpleadoEliminar/{nif}") {
        fun createRoute(nif: String) = "EmpleadoEliminar/$nif"
    }
    object EmpleadoFichaje: AppScreens("EmpleadoFichaje/{nif}") {
        fun createRoute(nif: String) = "EmpleadoFichaje/$nif"
    }

}