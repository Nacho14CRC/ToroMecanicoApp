package com.example.toromecanicoapp.state

import java.util.Date

data class LoginState(
	val sUsuario: String = "",
	val sContrasena: String = "",
	val bErrorIngreso: Boolean = false,
	var sNombreCompleto: String = "",
	var sIdentificacion: String = "",
	var sTelefono: String = "",
	var sCorreo: String = "",
	var sConfirmarContrasena: String = "",
	var nTipoCuenta: Int = 0,
	var fNacimiento: Date? = null,
	val bErrorRegistro: Boolean = false,
)