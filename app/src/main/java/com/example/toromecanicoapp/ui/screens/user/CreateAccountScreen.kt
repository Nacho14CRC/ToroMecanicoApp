package com.example.toromecanicoapp.ui.screens.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedEmailTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarPasswordTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.ui.screens.components.MostrarTextButton
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object CrearCuentaDestino : Destinos {
	override val ruta = "createAcount"
	override val tituloRecurso = R.string.correo_usuario
	override val descripcionIcono = ""
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CrearCuentaScreen(navegarALogin: () -> Unit, modelo: UserViewModel = viewModel()) {
	val context = LocalContext.current
	val email = rememberSaveable {
		mutableStateOf("")
	}
	val nombreCompleto = rememberSaveable {
		mutableStateOf("")
	}
	val password = rememberSaveable {
		mutableStateOf("")
	}
	val valido = remember(email.value, password.value) {
		email.value.trim().isNotEmpty() &&
				password.value.trim().isNotEmpty()
	}
	val scope = rememberCoroutineScope()
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoNombreCompleto = painterResource(id = R.drawable.ic_person)
	val iconoIdentificacion = painterResource(id = R.drawable.ic_person)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
	val iconoTelefono = painterResource(id = R.drawable.ic_phone)
	val iconoCalendario = painterResource(id = R.drawable.ic_calendar)
	val iconoUsuario = painterResource(id = R.drawable.ic_account_circle)
	val iconoContrasena = painterResource(id = R.drawable.ic_lock)
	val keyboardController = LocalSoftwareKeyboardController.current
	
	
	Column(
		modifier = Modifier
			.fillMaxSize(),
	) {
		Row(
			modifier = Modifier
				.padding(mediumPadding)
		) {
			Column(
				modifier = Modifier.fillMaxSize()
			) {
				Text(
					text = stringResource(R.string.crear_cuenta_text),
					style = MaterialTheme.typography.displayMedium,
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = stringResource(R.string.crearCuentaNueva),
					style = MaterialTheme.typography.labelSmall,
				)
				
				Spacer(modifier = Modifier.height(40.dp))
				MostrarOutlinedTextField(
					text = nombreCompleto,
					stringResource(R.string.nombre_usuario),
					stringResource(R.string.nombre_usuario_ph),
					iconoNombreCompleto,
					true
				)
				Spacer(modifier = Modifier.height(16.dp))
				/*MostrarOutlinedTextField(
					stringResource(R.string.identificacion_usuario),
					stringResource(R.string.identificacion_usuario_ph),
					modelo.sIdentificacion,
					iconoIdentificacion,
					true,
					LoginUiState.bErrorRegistro,
					{ modelo.ModificarIdentificacion(it) }
				)*/
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedEmailTextField(
					emailState = email,
					label = stringResource(R.string.correo_usuario),
					placeholder = stringResource(R.string.correo_usuario_ph),
					leadingIcon = iconoUsuario,
					singleLine = true
				)
				Spacer(modifier = Modifier.height(16.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Box(
						modifier = Modifier
							.weight(1f)
							.padding(end = 8.dp)
					) {
						/*MostrarOutlinedTextPhoneField(
							stringResource(R.string.telefono_usuario),
							stringResource(R.string.telefono_usuario_ph),
							modelo.sTelefono,
							iconoTelefono,
							true,
							LoginUiState.bErrorIngreso,
							{ modelo.ModificarUsuarioIngresado(it) },
							modifier = Modifier
						)*/
					}
					Box(
						modifier = Modifier
							.weight(1f)
					) {
					}
				}
				Spacer(modifier = Modifier.height(40.dp))
				MostrarPasswordTextField(
					passwordState = password,
					stringResource(R.string.login_contrasena),
					stringResource(R.string.empty_string),
					iconoContrasena,
				)
				Spacer(modifier = Modifier.height(16.dp))
				/*MostrarPasswordTextField(
					stringResource(R.string.confirmar_contrasena),
					stringResource(R.string.empty_string),
					modelo.sConfirmarContrasena,
					iconoContrasena,
					LoginUiState.bErrorIngreso,
					{ modelo.ModificarConfirmarContrasena(it)  }
				)*/
				Spacer(modifier = Modifier.height(40.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.btn_crear_cuenta),
					inputValido = valido
				) {
					keyboardController?.hide()
					scope.launch {
						createAccount(navegarALogin, context, modelo, email.value, password.value,nombreCompleto.value)
					}
				}
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = stringResource(R.string.antes_iniciar)
					)
					MostrarTextButton(
						sLabel = stringResource(R.string.login_button_text),
						onClick = {     navegarALogin() },
						modifier = Modifier
					)
				}
			}
		}
		
	}
}
private suspend fun createAccount(
	navigateToLogin: () -> Unit,
	context: Context,
	modelo: UserViewModel,
	email: String,
	password: String,
	nombreCompleto: String
) {
	when (val result = modelo.createUserWithEmailAndPassword(email, password,nombreCompleto)) {
		is AuthRes.Success -> {
			Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
			navigateToLogin()
		}
		
		is AuthRes.Error -> {
			Toast.makeText(context, "Error createAccount: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
		}
	}
}
