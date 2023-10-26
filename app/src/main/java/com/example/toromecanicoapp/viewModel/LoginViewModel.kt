package com.example.toromecanicoapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.toromecanicoapp.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(LoginState())
	val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
	var sUsuario by mutableStateOf("")
		private set
	var sContrasena by mutableStateOf("")
		private set
	fun ModificarUsuarioIngresado(sNuevoValor: String){
		sUsuario = sNuevoValor
	}
	fun ModificarContrasenaIngresado(sNuevoValor: String){
		sContrasena = sNuevoValor
	}
	fun Login(){
	
	}
	fun CrearCuenta(){
	
	}
	fun RestablecerContrasena(){
	
	}
}