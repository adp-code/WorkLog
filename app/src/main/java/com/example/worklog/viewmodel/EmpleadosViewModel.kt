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

class EmpleadosViewModel: ViewModel() {

        private val _nif = MutableLiveData<String>()
        val nif: LiveData<String> = _nif

        private val _nombre = MutableLiveData<String>()
        val nombre: LiveData<String> = _nombre

        private val _apellidos = MutableLiveData<String>()
        val apellidos: LiveData<String> = _apellidos

        private val _telefono = MutableLiveData<String>()
        val telefono: LiveData<String> = _telefono

        private val _email = MutableLiveData<String>()
        val email: LiveData<String> = _email

        private val _departamento = MutableLiveData<String>()
        val departamento: LiveData<String> = _departamento

        private val _uid = MutableLiveData<String>()
        val uid: LiveData<String> = _uid

        private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
        val empleados: StateFlow<List<Empleado>> = _empleados

        private val _isButtonEnable =MutableLiveData<Boolean>()
        val isButtonEnable: LiveData<Boolean> = _isButtonEnable

        fun onCompletedFields(nif:String, nombre:String, apellidos:String, email:String, telefono:String, departamento:String, uid:String) {
            _nif.value = nif
            _nombre.value = nombre
            _apellidos.value = apellidos
            _email.value = email
            _telefono.value = telefono
            _departamento.value = departamento
            _isButtonEnable.value = enableButton(nif, nombre, apellidos, telefono, departamento, email, uid)
        }

        fun enableButton(nif:String, nombre:String, apellidos:String, telefono:String, departamento:String, email:String, uid:String) =
            nif.length >0 && nombre.length >0 && apellidos.length>0 && telefono.length>0 && departamento.length>0 && email.length>0
    }

