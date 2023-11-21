package com.example.toromecanicoapp.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreenViewModel : ViewModel() {
	private val auth: FirebaseAuth = Firebase.auth
	fun Login(email:String, password:String) {
		Log.d("Login","Entró")
		try {
			auth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener { task ->
					if (task.isSuccessful){
						Log.d("Firebase", "signInWithEmailAndPassword logueado")
					} else {
						Log.d("Firebase", "Error : ${task.result.toString()}")
					}
				}
		} catch (ex: Exception){
			Log.d("Firebase", "signInWithEmailAndPassword: ${ex.message}")
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