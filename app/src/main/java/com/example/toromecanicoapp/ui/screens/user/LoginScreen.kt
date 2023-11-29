package com.example.toromecanicoapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.toromecanicoapp.ui.screens.components.MostrarPasswordTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.ui.screens.components.MostrarTextButton
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object LoginDestino : Destinos {
	override val ruta = "login"
	override val tituloRecurso = R.string.correo_usuario
	override val descripcionIcono = ""
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
	navegarARecuperarContrasena: () -> Unit,
	navegarACrearCuenta: () -> Unit,
	navegarAInicio: () -> Unit,
	modelo: UserViewModel = viewModel()
) {
	val email = rememberSaveable { mutableStateOf("andreifff@msn.com") }
	val password = rememberSaveable { mutableStateOf("123456") }
	val context = LocalContext.current
	val keyboardController = LocalSoftwareKeyboardController.current
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
	val iconoContrasena = painterResource(id = R.drawable.ic_lock)
	val scope = rememberCoroutineScope()
	
	//Validacion de pantalla
	val bHabilitarBoton = remember(email.value, password.value) {
		email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
	}
	val errorCorreo = remember { mutableStateOf<String?>(null) }
	val errorContrasena = remember { mutableStateOf<String?>(null) }
	
	
	
	Column(
		modifier = Modifier.fillMaxSize(),
	) {
		Row(
			modifier = Modifier
				.weight(0.50f)
				.padding(mediumPadding)
		) {
			Column(
				modifier = Modifier
					.fillMaxSize(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.SpaceBetween
			) {
				Image(
					painter = painterResource(R.drawable.ic_login), contentDescription = null,
					modifier =
					Modifier
						.size(width = 200.dp, height = 200.dp)
				)
				Spacer(modifier = Modifier.height(60.dp))
				Text(
					text = stringResource(R.string.bienvenida),
					style = MaterialTheme.typography.displayMedium,
				)
			}
		}
		Row(
			modifier = Modifier
				.weight(0.50f)
				.padding(mediumPadding)
		) {
			Column(
				modifier = Modifier.fillMaxSize()
			) {
				MostrarOutlinedEmailTextField(
					valor = email,
					label = stringResource(R.string.correo_usuario),
					placeholder = stringResource(R.string.correo_usuario_ph),
					leadingIcon = iconoCorreo,
					singleLine = true,
					mensajeError = errorCorreo
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					valor = password,
					stringResource(R.string.login_contrasena),
					stringResource(R.string.empty_string),
					iconoContrasena,
					mensajeError = errorContrasena
				)
				MostrarTextButton(
					sLabel = stringResource(R.string.olvido_contrasena_text),
					onClick = { navegarARecuperarContrasena() },
					modifier = Modifier.align(Alignment.End)
				)
				Spacer(modifier = Modifier.height(40.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.login_button_text),
					habilitarBoton = bHabilitarBoton
				) {
					keyboardController?.hide()
					scope.launch {
						scope.launch {
							login(
								navegarAInicio,
								context,
								modelo,
								email.value,
								password.value,
								errorCorreo,
								errorContrasena
							)
						}
					}
				}
				Spacer(modifier = Modifier.height(8.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = stringResource(R.string.antes_crear_cuenta)
					)
					MostrarTextButton(
						sLabel = stringResource(R.string.crear_cuenta_text),
						onClick = { navegarACrearCuenta() },
						modifier = Modifier
					)
				}
			}
		}
	}
}

private suspend fun login(
	navigateToHome: () -> Unit,
	context: Context,
	modelo: UserViewModel,
	email: String,
	password: String,
	errorCorreo: MutableState<String?>,
	errorContrasena: MutableState<String?>
) {
	var valido = true
	if (email.trim().isNullOrEmpty()) {
		valido = false
	} else {
		if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
			errorCorreo.value = "Correo inválido"
			valido = false
		}
	}
	
	if (password.trim().isNullOrEmpty()) {
		valido = false
	} else {
		if (password.trim().length < 6) {
			errorContrasena.value = "La contraseña debe tener al menos 6 caracteres"
			valido = false
		}
	}
	if (valido) {
		when (val result = modelo.signInWithEmailAndPassword(email, password)) {
			is AuthRes.Success -> {
				navigateToHome()
			}
			
			is AuthRes.Error -> {
				Toast.makeText(context, "Error: ${result.errorMessage}", Toast.LENGTH_SHORT)
					.show()
			}
		}
	}
	
}

