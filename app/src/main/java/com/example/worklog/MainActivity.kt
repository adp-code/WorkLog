package com.example.worklog

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.worklog.navigation.AppNavigation
import com.example.worklog.ui.theme.WorkLogTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    // Sobrescribe attachBaseContext para aplicar el locale almacenado
    override fun attachBaseContext(newBase: Context) {
        // Lee el idioma seleccionado de SharedPreferences; si no existe, usa "en" (inglés) por defecto
        val sharedPrefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val language = sharedPrefs.getString("selected_language", "en") ?: "en"
        val locale = Locale(language)
        // Establece el locale por defecto
        Locale.setDefault(locale)

        // Crea una nueva configuración y actualiza el locale
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        // Crea un nuevo contexto con la configuración actualizada
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            WorkLogTheme {
                AppNavigation(auth)
            }
        }
    }
}
