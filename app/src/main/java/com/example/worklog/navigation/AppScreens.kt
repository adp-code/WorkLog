package com.example.worklog.navigation

sealed class AppScreens (val ruta:String) {
    object Login: AppScreens("Login")
    object Home: AppScreens("Home")
}