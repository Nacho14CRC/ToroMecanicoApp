package com.example.toromecanicoapp.data.model

data class Cita(
	var id: String?,
	val userId: String,
	val observaciones: String
) {
	constructor() : this( "", "", "")
	
	fun toMap(): MutableMap<String, Any> {
		return mutableMapOf(
			"userId" to this.userId,
			"observaciones" to this.observaciones
		)
	}
}