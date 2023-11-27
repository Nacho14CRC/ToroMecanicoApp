package com.example.toromecanicoapp.ui.screens.cita

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CitaViewModel : ViewModel() {
	private val firestore = FirebaseFirestore.getInstance()
	
	
	suspend fun addCita(userId: String?, observaciones: String): AuthRes<Unit> {
		return try {
			val newCita = Cita(
				documentId = null,
				userId = userId.toString(),
				observaciones = observaciones
			).toMap()
			firestore.collection("citas").add(newCita).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al al agregar la cita")
		}
	}
	
	fun getCita(documentId: String, callback: (AuthRes<Cita?>) -> Unit) {
		viewModelScope.launch {
			try {
				val cita = withContext(Dispatchers.IO) {
					val documentSnapshot =
						firestore.collection("citas").document(documentId).get().await()
					documentSnapshot.toObject(Cita::class.java)
				}
				callback(AuthRes.Success(cita))
			} catch (e: Exception) {
				callback(AuthRes.Error(e.message ?: "Error al obtener la cita"))
			}
		}
	}
	
}