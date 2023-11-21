package com.example.toromecanicoapp.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
	private val auth: FirebaseAuth = Firebase.auth
	private val _loading = MutableLiveData(false)
	fun Login(email:String, password:String, home: ()-> Unit)
			= viewModelScope.launch {
		
		try {
			auth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener { task ->
					if (task.isSuccessful){
						Log.d("toroMecanicoApp", "Login: logueado")
						home()
						
					} else {
						Log.d("toroMecanicoApp", "Login Error : ${task.result.toString()}")
					}
				}
		} catch (ex: Exception){
			Log.d("toroMecanicoApp", "Login Catch: ${ex.message}")
		}
	
	}
	fun CrearCuenta() {
	
	}
	
	fun NavegarACrearCuenta(navController: NavHostController) {
		navController.navigate("r2") {
			popUpTo(navController.graph.startDestinationId)
		}
	}
	
	fun NavegarARestablerContrasena(navController: NavHostController) {
		navController.navigate("r3") {
			popUpTo(navController.graph.startDestinationId)
		}
	}
}