package com.example.toromecanicoapp.data.model

data class User(
	var id: String?,
	val userId: String,
	val identificacion: String,
	val nombreCompleto: String,
	val correo: String,
	val telefono: String,
	val tipoUsuario: String
) {
	constructor() : this("", "", "", "", "", "", "")
	fun toMap(): MutableMap<String, Any> {
		return mutableMapOf(
			"user_id" to this.userId,
			"identificacion" to this.identificacion,
			"nombreCompleto" to this.nombreCompleto,
			"correo" to this.correo,
			"telefono" to this.telefono,
			"tipoUsuario" to this.tipoUsuario
		)
	}
}
