package com.example.toromecanicoapp.ui.screens.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.ui.screens.components.MostrarTextButton
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object RecuperarContrasenaDestino : Destinos {
	override val ruta = "resetPassword"
	override val tituloRecurso = R.string.correo_campo
	override val descripcionIcono = ""
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecuperarContrasenaScreen(navegarALogin: () -> Unit, modelo: UserViewModel = viewModel()) {
	val correo = rememberSaveable {
		mutableStateOf("")
	}
	val valido = remember(correo.value) {
		correo.value.trim().isNotEmpty()
	}
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
	val keyboardController = LocalSoftwareKeyboardController.current
	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	val errorCorreo = remember { mutableStateOf<String?>(null) }
	
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
					text = stringResource(R.string.restablecer_contrasena_principal),
					style = MaterialTheme.typography.displayMedium,
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = stringResource(R.string.restablecer_contrasena_secundario),
					style = MaterialTheme.typography.labelSmall,
				)
				
				Spacer(modifier = Modifier.height(40.dp))
				
				MostrarOutlinedEmailTextField(
					valor = correo,
					label = stringResource(R.string.correo_campo),
					placeholder = stringResource(R.string.correo_usuario_ph),
					leadingIcon = iconoCorreo,
					singleLine = true,
					mensajeError = errorCorreo
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.btn_restablecer_text),
					habilitarBoton = valido
				) {
					keyboardController?.hide()
					scope.launch {
						forgotPassword(navegarALogin, context, modelo, correo.value, errorCorreo)
					}
				}
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					MostrarTextButton(
						sLabel = stringResource(R.string.link_volver_iniciosesion_text),
						onClick = { navegarALogin() },
						modifier = Modifier
					)
				}
			}
		}
		
	}
}

private suspend fun forgotPassword(
	navigateToLogin: () -> Unit,
	context: Context,
	modelo: UserViewModel,
	correo: String,
	errorCorreo: MutableState<String?>
) {
	var valido = true
	
	if (correo.trim().isNullOrEmpty()) {
		valido = false
	} else {
		if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()) {
			errorCorreo.value = "Correo inválido"
			valido = false
		}
	}
	
	if (valido) {
		when (val result = modelo.resetPassword(correo)) {
			is AuthRes.Success -> {
				Toast.makeText(context, "Correo enviado", Toast.LENGTH_SHORT).show()
				navigateToLogin()
			}
			
			is AuthRes.Error -> {
				Toast.makeText(context, "Error al enviar el correo", Toast.LENGTH_SHORT).show()
			}
		}
	}
}
