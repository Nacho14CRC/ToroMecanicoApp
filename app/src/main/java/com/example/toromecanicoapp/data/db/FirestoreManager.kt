package com.example.toromecanicoapp.data.db

import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.viewmodels.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreManager(userModel: UserViewModel) {
	private val firestore = FirebaseFirestore.getInstance()
	var userId = userModel.getCurrentUser()?.uid
	
	fun getCitasFlow(): Flow<List<Cita>> = callbackFlow {
		val citasRef =
			firestore.collection("citas").whereEqualTo("userId", userId).orderBy("userId")
		
		val subscription = citasRef.addSnapshotListener { snapshot, _ ->
			snapshot?.let { querySnapshot ->
				val citas = mutableListOf<Cita>()
				for (document in querySnapshot.documents) {
					val documentId = document.id
					val cita = document.toObject(Cita::class.java)
					if (cita != null) {
						cita.documentId = documentId
					};
					cita?.let { citas.add(it) }
				}
				trySend(citas).isSuccess
			}
		}
		awaitClose { subscription.remove() }
	}
}