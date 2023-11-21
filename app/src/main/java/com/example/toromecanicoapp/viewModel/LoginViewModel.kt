package com.example.toromecanicoapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.toromecanicoapp.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class LoginViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(LoginState())
	val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
	var sUsuario by mutableStateOf("")
		private set
	var sContrasena by mutableStateOf("")
		private set
	var sNombreCompleto by mutableStateOf("")
		private set
	var sIdentificacion by mutableStateOf("")
		private set
	var sTelefono by mutableStateOf("")
		private set
	var sCorreo by mutableStateOf("")
		private set
	var sConfirmarContrasena by mutableStateOf("")
		private set
	var nTipoCuenta by mutableStateOf(0)
		private set
	var fNacimiento by  mutableStateOf<Date?>(null)
		private set
	
	fun ModificarUsuarioIngresado(sNuevoValor: String) {
		sUsuario = sNuevoValor
	}
	
	fun ModificarContrasenaIngresado(sNuevoValor: String) {
		sContrasena = sNuevoValor
	}
	
	fun ModificarNombreCompleto(sNuevoValor: String) {
		sNombreCompleto = sNuevoValor
	}
	fun ModificarIdentificacion(sNuevoValor: String) {
		sIdentificacion = sNuevoValor
	}
	
	fun ModificarCorreo(sNuevoValor: String) {
		sCorreo = sNuevoValor
	}
	fun ModificarConfirmarContrasena(sNuevoValor: String) {
		sConfirmarContrasena= sNuevoValor
	}
	
	fun Login() {
	
	}
	fun CrearCuenta() {
	
	}
	
	fun NavegarACrearCuenta(navController: NavHostController) {
		navController.navigate("r2") {
			popUpTo(navController.graph.startDestinationId)
		}
	}
	
	fun NavegarARestablerContrasena(navController: NavHostController) {
		navController.navigate("r3") {
			popUpTo(navController.graph.startDestinationId)
		}
	}
}