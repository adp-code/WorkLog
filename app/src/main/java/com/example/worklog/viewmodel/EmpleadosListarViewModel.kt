package com.example.worklog.viewmodel

import androidx.lifecycle.ViewModel
import com.example.worklog.models.Empleado
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EmpleadosListarViewModel: ViewModel() {

    suspend fun getEmpleadosViewModel(): List<Empleado> {
            return try {
                // Obtener instancia de Firebase Firestore
                val db = FirebaseFirestore.getInstance()

                //Almacenar el nombre de la colección
                var nombre_coleccion = "empleados"

                val query = db.collection(nombre_coleccion).get().await()

                val empleados = mutableListOf<Empleado>()

                for (document in query.documents) {
                    val empleado = document.toObject(Empleado::class.java)
                    if (empleado != null) {
                        empleados.add(empleado)
                    }
                }
                query.toObjects(Empleado::class.java)

            } catch (e: Exception) {
                emptyList()
            }
        }


    }

