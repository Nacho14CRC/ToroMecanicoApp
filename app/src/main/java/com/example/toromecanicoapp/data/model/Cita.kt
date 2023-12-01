package com.example.toromecanicoapp.data.model

data class Cita(
	var id: String?,
	val userId: String,
	val observaciones: String,
	val fechaCita: String,
	val mecanico: String,
	val estado: String
) {
	constructor() : this( "", "", "","","","")
	
	fun toMap(): MutableMap<String, Any> {
		return mutableMapOf(
			"userId" to this.userId,
			"observaciones" to this.observaciones,
			"fechaCita" to this.fechaCita,
			"mecanico" to this.mecanico,
			"estado" to this.estado
		)
	}
}
