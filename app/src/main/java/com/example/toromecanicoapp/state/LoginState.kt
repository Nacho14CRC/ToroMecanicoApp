package com.example.toromecanicoapp.state

data class LoginState(
	val sUsuario: String  = "",
	val sContrasena: String  = "",
	val bErrorIngreso: Boolean = false
	
)