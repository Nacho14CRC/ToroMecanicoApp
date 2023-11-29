package com.example.toromecanicoapp.ui.screens.cita

import androidx.lifecycle.ViewModel
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CitaViewModel : ViewModel() {
	private val firestore = FirebaseFirestore.getInstance()
	
	fun GetCitas(userId: String?): Flow<List<Cita>> = callbackFlow {
		val citasRef =
			firestore.collection("citas").whereEqualTo("userId", userId).orderBy("userId")
		
		val subscription = citasRef.addSnapshotListener { snapshot, _ ->
			snapshot?.let { querySnapshot ->
				val citas = mutableListOf<Cita>()
				for (document in querySnapshot.documents) {
					val documentId = document.id
					val cita = document.toObject(Cita::class.java)
					if (cita != null) {
						cita.id = documentId
					};
					cita?.let { citas.add(it) }
				}
				trySend(citas).isSuccess
			}
		}
		awaitClose { subscription.remove() }
	}
	
	fun GetCitaByDocument(id: String): Flow<Cita> = callbackFlow {
		val citasRef = firestore.collection("citas").document(id)
		
		val subscription = citasRef.addSnapshotListener { snapshot, error ->
			if (error != null) {
				close(error)
				return@addSnapshotListener
			}
			snapshot?.let { querySnapshot ->
				try {
					val cita = querySnapshot.toObject(Cita::class.java)
					if (cita != null) {
						cita.id = id
						trySend(cita).isSuccess
					}
				} catch (e: Exception) {
					close(e)
				}
			}
		}
		
		awaitClose { subscription.remove() }
	}
	
	suspend fun CancelarCita(documentId: String?) {
		val citaRef = firestore.collection("citas").document(documentId.toString())
		citaRef.delete().await()
	}
	
	suspend fun AgregarCita(
		userId: String?,
		observaciones: String,
		fechaCita: String,
		mecanico: String
	): AuthRes<Unit> {
		return try {
			val newCita = Cita(
				id = null,
				userId = userId.toString(),
				observaciones = observaciones,
				fechaCita = fechaCita,
				mecanico = mecanico,
			).toMap()
			firestore.collection("citas").add(newCita).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al agregar la cita")
		}
	}
	
	suspend fun EditarCita(id: String, userId: String,fechaCita:String,mecanico:String, observaciones: String): AuthRes<Unit> {
		return try {
			val newCita = Cita(
				id = id,
				userId = userId,
				observaciones = observaciones,
				fechaCita = fechaCita,
				mecanico = mecanico,
			).toMap()
			firestore.collection("citas").document(id).set(newCita).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al agregar la cita")
		}
	}
	
}