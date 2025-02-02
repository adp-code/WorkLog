package com.example.worklog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worklog.models.Empleado
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EmpleadosViewModel() {

        private val _nif = MutableLiveData<String>()
        val nif: LiveData<String> = _nif

        private val _nombre = MutableLiveData<String>()
        val nombre: LiveData<String> = _nombre

        private val _apellidos = MutableLiveData<String>()
        val apellidos: LiveData<String> = _apellidos

        private val _telefono = MutableLiveData<String>()
        val telefono: LiveData<String> = _telefono

        private val _departamento = MutableLiveData<String>()
        val departamento: LiveData<String> = _departamento

        private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
        val empleados: StateFlow<List<Empleado>> = _empleados

        private val _isButtonEnable =MutableLiveData<Boolean>()
        val isButtonEnable: LiveData<Boolean> = _isButtonEnable

        fun onCompletedFields(nif:String, nombre:String, apellidos:String, telefono:String, departamento:String) {
            _nif.value = nif
            _nombre.value = nombre
            _apellidos.value = apellidos
            _telefono.value = telefono
            _departamento.value = departamento
            _isButtonEnable.value = enableButton(nif, nombre)
        }

        fun enableButton(nif:String, nombre:String) =
            nif.length >0 && nombre.length >0
    }

