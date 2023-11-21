package com.example.toromecanicoapp.screens.login

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel() : ViewModel() {
	private val auth: FirebaseAuth = Firebase.auth

	fun Login(email: String, password: String, context: Context, home: ()-> Unit) {
		auth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Log.d("Firebase", "signInWithEmailAndPassword logueado")
					home()
				}
			}
			.addOnFailureListener { exception ->
				when (exception) {
					is FirebaseAuthInvalidCredentialsException -> {
						mostrarAlerta("Credenciales inválidas", context)
					}
					else -> {
						mostrarAlerta(exception.message ?: "Error desconocido", context)
					}
				}
			}
	}

	private fun mostrarAlerta(mensaje: String, context: Context) {
		val builder = AlertDialog.Builder(context)
		builder.setTitle("Error de autenticación")
		builder.setMessage(mensaje)
		builder.setPositiveButton("Aceptar", null)
		val alertDialog: AlertDialog = builder.create()
		alertDialog.show()
	}

	fun CrearCuenta(email:String, password:String, context: Context) {
		auth.createUserWithEmailAndPassword(email, password)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Log.d("Firebase", "createUserWithEmailAndPassword creado")
				}
			}
			.addOnFailureListener { exception ->
				mostrarAlerta(exception.message ?: "Error desconocido", context)
			}
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