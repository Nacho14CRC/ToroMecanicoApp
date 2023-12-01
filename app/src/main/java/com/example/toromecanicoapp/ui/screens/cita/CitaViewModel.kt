package com.example.toromecanicoapp.ui.screens.cita

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.data.model.User
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.google.android.gms.auth.api.phone.SmsRetrieverStatusCodes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CitaViewModel : ViewModel() {
	private val firestore = FirebaseFirestore.getInstance()
	
	fun GetCitas(user: User?): Flow<List<Cita>> = callbackFlow {
		val citasRef: Query = if (user?.tipoUsuario != "Mecánico") {
			firestore.collection("citas")
				.whereEqualTo("userId", user?.id)
				.whereEqualTo("estado", "PEN")
				.orderBy("userId")
		} else {
			firestore.collection("citas")
				.whereEqualTo("mecanico", user?.nombreCompleto)
				.whereEqualTo("estado", "PEN")
				.orderBy("userId")
		}
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
	
	fun EnviarMensajeCita(context: Context, telefonoMensaje: String, mensaje: String) {
		if (ContextCompat.checkSelfPermission(
				context,
				Manifest.permission.SEND_SMS
			) != PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(
				context as Activity,
				arrayOf(Manifest.permission.SEND_SMS),
				SmsRetrieverStatusCodes.USER_PERMISSION_REQUIRED
			)
		} else {
			val smsManager = SmsManager.getDefault()
			val sentIntent = Intent("SMS_SENT")
			val deliveredIntent = Intent("SMS_DELIVERED")
			
			val sentPI = PendingIntent.getBroadcast(
				context, 0, sentIntent,
				PendingIntent.FLAG_IMMUTABLE
			)
			val deliveredPI = PendingIntent.getBroadcast(
				context, 0, deliveredIntent,
				PendingIntent.FLAG_IMMUTABLE
			)
			
			smsManager.sendTextMessage(telefonoMensaje, null, mensaje, sentPI, deliveredPI)
			
		}
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
				estado = "PEN"
			).toMap()
			firestore.collection("citas").add(newCita).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al agregar la cita")
		}
	}
	
	suspend fun EditarCita(
		id: String,
		userId: String,
		fechaCita: String,
		mecanico: String,
		observaciones: String,
		estado: String
	): AuthRes<Unit> {
		return try {
			val newCita = Cita(
				id = id,
				userId = userId,
				observaciones = observaciones,
				fechaCita = fechaCita,
				mecanico = mecanico,
				estado = estado
			).toMap()
			firestore.collection("citas").document(id).set(newCita).await()
			AuthRes.Success(Unit)
		} catch (e: Exception) {
			AuthRes.Error(e.message ?: "Error al agregar la cita")
		}
	}
	
}