package com.example.toromecanicoapp.data

data class User(
	val id: String?,
	val userId: String,
	val fullName: String
){

	fun toMap(): MutableMap<String,Any>{
		return  mutableMapOf(
			"user_id" to this.userId,
			"fullName" to this.fullName
		)
	}
}
