package com.example.toromecanicoapp.data

data class Usuario(
	val id: String?,
	val userId: String,
	val nombreCompleto: String
){

	fun toMap(): MutableMap<String,Any>{
		return  mutableMapOf(
			"user_id" to this.userId,
			"nombre_completo" to this.nombreCompleto
		)
	}
}
