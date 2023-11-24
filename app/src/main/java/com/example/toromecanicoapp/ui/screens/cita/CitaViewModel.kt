package com.example.toromecanicoapp.ui.screens.cita

import androidx.lifecycle.ViewModel
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CitaViewModel : ViewModel() {
	private val firestore = FirebaseFirestore.getInstance()
	
	
	suspend fun addCita(userId: String?, observaciones: String): AuthRes<Unit> {
		return try {
			val newCita = Cita(
				id = null,
				userId = userId.toString(),
				observaciones = observaciones
			).toMap()
			firestore.collection("citas").add(newCita).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al al agregar la cita")
		}
	}
}