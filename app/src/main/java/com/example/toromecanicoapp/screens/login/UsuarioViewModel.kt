package com.example.toromecanicoapp.screens.login

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.toromecanicoapp.data.Usuario
import com.example.toromecanicoapp.navegacion.toroMecanicoScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UsuarioViewModel : ViewModel() {
	private val auth: FirebaseAuth = Firebase.auth
	private val _loading = MutableLiveData(false)

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

	fun CrearCuenta(email:String, password:String,nombreCompleto:String, context: Context, home: ()-> Unit) {
		if (_loading.value == false){
			_loading.value = true
		auth.createUserWithEmailAndPassword(email, password)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					crearUsuarioDB(email, password,nombreCompleto)
					Log.d("Firebase", "createUserWithEmailAndPassword creado")
					home()
				}
			}
			.addOnFailureListener { exception ->
				mostrarAlerta(exception.message ?: "Error desconocido", context)
			}
		}
	}
	
	fun RestablecerContrasena(email: String) {
		auth.sendPasswordResetEmail(email)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Log.d("RestablecerContrasena", "Correo enviado")
				} else {
					Log.d("RestablecerContrasena", "Fallo el envi0")
				}
			}
	}
	private fun crearUsuarioDB(email: String, password: String, nombreCompleto: String) {
		val userId= auth.currentUser?.uid
		
		val newUsuario = Usuario(
			userId=userId.toString(),
			nombreCompleto = nombreCompleto.toString(),
			id= null
		).toMap()
		
		FirebaseFirestore.getInstance().collection("usuarios")
			.add(newUsuario)
			.addOnSuccessListener {
				Log.d("Firebase", "Usuario agregado con el ID: ${it.id}")
			}
			.addOnFailureListener { e ->
				Log.d("Firebase", "Error adding user", e)
			}
		
	}
	
	fun NavegarALogin(navController: NavHostController) {
		navController.navigate(toroMecanicoScreens.LoginScreen.name)
	}
	fun NavegarACrearCuenta(navController: NavHostController) {
		navController.navigate(toroMecanicoScreens.CrearCuentaScreen.name)
	}
	
	fun NavegarARestablerContrasena(navController: NavHostController) {
		navController.navigate(toroMecanicoScreens.RestablecerContrasenaScreen.name)
	}
}