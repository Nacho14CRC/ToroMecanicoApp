package com.example.toromecanicoapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toromecanicoapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

sealed class AuthRes<out T> {
	data class Success<T>(val data: T) : AuthRes<T>()
	data class Error(val errorMessage: String) : AuthRes<Nothing>()
}

class UserViewModel : ViewModel() {
	private val auth: FirebaseAuth by lazy { Firebase.auth }
	private val _loading = MutableLiveData(false)
	private val firestore = FirebaseFirestore.getInstance()
	
	fun getCurrentUser(): FirebaseUser? {
		return auth.currentUser
	}
	
	fun GetByDocument(id: String): Flow<User?> = callbackFlow {
		val usuariosRef = firestore.collection("usuarios")
			.whereEqualTo("user_id", id)
		
		val subscription = usuariosRef.addSnapshotListener { snapshot, error ->
			if (error != null) {
				close(error)
				return@addSnapshotListener
			}
			snapshot?.let { querySnapshot ->
				try {
					val usuario = querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
					if (usuario != null) {
						trySend(usuario).isSuccess
						close() // Cerrar el flujo después de enviar el usuario
					} else {
						close() // Cerrar el flujo si no se encuentra ningún usuario
					}
				} catch (e: Exception) {
					close(e)
				}
			}
		}
		
		awaitClose { subscription.remove() }
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
		identificacion: String,
		nombreCompleto: String,
		correo: String,
		telefono: String,
		tipoUsuario: String,
		password: String
	): AuthRes<FirebaseUser?> {
		if (_loading.value == false) {
			_loading.value = true
			
			return try {
				val authResult = auth.createUserWithEmailAndPassword(correo, password).await()
				addAdicionalInformationUser(
					identificacion,
					nombreCompleto,
					telefono,
					correo,
					tipoUsuario
				)
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
	
	private fun addAdicionalInformationUser(
		identificacion: String,
		nombreCompleto: String,
		telefono: String,
		correo: String,
		tipoUsuario: String
	) {
		val userId = auth.currentUser?.uid
		
		val newUsuario = User(
			id = null,
			userId = userId.toString(),
			identificacion = identificacion,
			nombreCompleto = nombreCompleto,
			telefono = telefono,
			correo = correo,
			tipoUsuario = tipoUsuario
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
}