package com.example.toromecanicoapp.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toromecanicoapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

sealed class AuthRes<out T> {
	data class Success<T>(val data: T) : AuthRes<T>()
	data class Error(val errorMessage: String) : AuthRes<Nothing>()
}

class UserViewModel : ViewModel() {
	private val auth: FirebaseAuth by lazy { Firebase.auth }
	private val _loading = MutableLiveData(false)
	
	fun getCurrentUser(): FirebaseUser? {
		return auth.currentUser
	}
	
	suspend fun signInWithEmailAndPassword(
		email: String,
		password: String
	): AuthRes<FirebaseUser?> {
		return try {
			val authResult = auth.signInWithEmailAndPassword(email, password).await()
			AuthRes.Success(authResult.user)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al iniciar sesión")
		}
	}
	
	suspend fun resetPassword(email: String): AuthRes<Unit> {
		return try {
			auth.sendPasswordResetEmail(email).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al restablecer la contraseña")
		}
	}
	
	suspend fun createUserWithEmailAndPassword(
		email: String,
		password: String,
		nombreCompleto: String
	): AuthRes<FirebaseUser?> {
		if (_loading.value == false) {
			_loading.value = true
			
			return try {
				val authResult = auth.createUserWithEmailAndPassword(email, password).await()
				crearUsuarioDB(email, password, nombreCompleto)
				AuthRes.Success(authResult.user)
			} catch (e: Exception) {
				AuthRes.Error(e.message ?: "Error al crear el usuario")
			}
		} else {
			return AuthRes.Error("La operación está en curso. Por favor, espere.")
		}
	}
	
	fun signOut() {
		auth.signOut()
	}
	
	private fun crearUsuarioDB(email: String, password: String, nombreCompleto: String) {
		val userId = auth.currentUser?.uid
		
		val newUsuario = User(
			userId = userId.toString(),
			fullName = nombreCompleto.toString(),
			id = null
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
	
	//TODO AAC
	private fun mostrarAlerta(mensaje: String, context: Context) {
		val builder = AlertDialog.Builder(context)
		builder.setTitle("Error de autenticación")
		builder.setMessage(mensaje)
		builder.setPositiveButton("Aceptar", null)
		val alertDialog: AlertDialog = builder.create()
		alertDialog.show()
	}
	
}