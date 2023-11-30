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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextNumericoField
import com.example.toromecanicoapp.ui.screens.components.MostrarPasswordTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.ui.screens.components.MostrarTextButton
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object CrearCuentaDestino : Destinos {
	override val ruta = "createAcount"
	override val tituloRecurso = R.string.correo_campo
	override val descripcionIcono = ""
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable

fun CrearCuentaScreen(navegarALogin: () -> Unit, userModel: UserViewModel = viewModel()) {
	val context = LocalContext.current
	val identificacion = rememberSaveable {
		mutableStateOf("")
	}
	val nombreCompleto = rememberSaveable {
		mutableStateOf("")
	}
	val correo = rememberSaveable {
		mutableStateOf("")
	}
	val telefono = rememberSaveable {
		mutableStateOf("")
	}
	val password = rememberSaveable {
		mutableStateOf("")
	}
	val confirmarContrasena = rememberSaveable {
		mutableStateOf("")
	}
	val scope = rememberCoroutineScope()
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoIdentificacion = painterResource(id = R.drawable.ic_identificacion)
	val iconoNombreCompleto = painterResource(id = R.drawable.ic_nombre_completo)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
	val iconoTelefono = painterResource(id = R.drawable.ic_phone)
	val iconoTipoUsuario = painterResource(id = R.drawable.ic_tipo_usuario)
	val iconoContrasena = painterResource(id = R.drawable.ic_lock)
	val keyboardController = LocalSoftwareKeyboardController.current
	//Combo
	val lstTipoCliente = listOf("", "Cliente", "Mecánico")
	var expandirComboTipo by remember { mutableStateOf(false) }
	val tipoUsuario = rememberSaveable {
		mutableStateOf(lstTipoCliente[0])
	}
	
	val errorIdentificacion = remember { mutableStateOf<String?>(null) }
	val errorNombreCompleto = remember { mutableStateOf<String?>(null) }
	val errorCorreo = remember { mutableStateOf<String?>(null) }
	val errorTelefono = remember { mutableStateOf<String?>(null) }
	val errorFechaNacimiento = remember { mutableStateOf<String?>(null) }
	val errorTipoUsuario = remember { mutableStateOf<String?>(null) }
	val errorContrasena = remember { mutableStateOf<String?>(null) }
	val errorContrasenaConfirmar = remember { mutableStateOf<String?>(null) }
	
	val bHabilitarBoton = remember(
		identificacion.value,
		nombreCompleto.value,
		correo.value,
		telefono.value,
		tipoUsuario.value,
		password.value,
		confirmarContrasena.value
	) {
		identificacion.value.trim().isNotEmpty() &&
				nombreCompleto.value.trim().isNotEmpty() &&
				correo.value.trim().isNotEmpty() &&
				telefono.value.trim().isNotEmpty() &&
				tipoUsuario.value.trim().isNotEmpty() &&
				password.value.trim().isNotEmpty() &&
				confirmarContrasena.value.trim().isNotEmpty()
	}
	
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
					text = stringResource(R.string.link_crear_cuenta_text),
					style = MaterialTheme.typography.displayMedium,
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = stringResource(R.string.crear_cuenta_nueva_secundario),
					style = MaterialTheme.typography.labelSmall,
				)
				
				Spacer(modifier = Modifier.height(8.dp))
				MostrarOutlinedTextNumericoField(
					text = identificacion,
					stringResource(R.string.identificacion_campo),
					stringResource(R.string.identificacion_usuario_ph),
					iconoIdentificacion,
					true,
					mensajeError = errorIdentificacion,
					modifier = Modifier.fillMaxWidth()
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedTextField(
					text = nombreCompleto,
					stringResource(R.string.nombre_completo_campo),
					stringResource(R.string.nombre_usuario_ph),
					iconoNombreCompleto,
					true,
					mensajeError = errorNombreCompleto
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedEmailTextField(
					valor = correo,
					label = stringResource(R.string.correo_campo),
					placeholder = stringResource(R.string.correo_usuario_ph),
					leadingIcon = iconoCorreo,
					singleLine = true,
					mensajeError = errorCorreo
				)
				Spacer(modifier = Modifier.height(16.dp))
				
				MostrarOutlinedTextNumericoField(
					text = telefono,
					stringResource(R.string.telefono_campo),
					stringResource(R.string.empty_string),
					iconoTelefono,
					true,
					mensajeError = errorTelefono,
					modifier = Modifier.fillMaxWidth()
				)
				Spacer(modifier = Modifier.height(16.dp))
				ExposedDropdownMenuBox(
					expanded = expandirComboTipo,
					onExpandedChange = { expandirComboTipo = !expandirComboTipo }
				) {
					TextField(
						modifier = Modifier
							.menuAnchor()
							.fillMaxWidth(),
						readOnly = true,
						value = tipoUsuario.value,
						onValueChange = { },
						label = { Text("Tipo de usuario") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirComboTipo) },
						leadingIcon = {
							Icon(
								painter = iconoTipoUsuario,
								contentDescription = null
							)
						},
						colors = ExposedDropdownMenuDefaults.textFieldColors(
							focusedContainerColor = MaterialTheme.colorScheme.surface,
							unfocusedContainerColor = MaterialTheme.colorScheme.surface,
							disabledContainerColor = MaterialTheme.colorScheme.surface,
						),
					)
					ExposedDropdownMenu(
						expanded = expandirComboTipo,
						onDismissRequest = { expandirComboTipo = false },
					) {
						lstTipoCliente.forEach { selectionOption ->
							DropdownMenuItem(
								text = { Text(selectionOption) },
								onClick = {
									tipoUsuario.value = selectionOption
									expandirComboTipo = false
								},
								contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
							)
						}
					}
				}
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					valor = password,
					stringResource(R.string.contrasena_campo),
					stringResource(R.string.contrasena_ph),
					iconoContrasena,
					mensajeError = errorContrasena
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					valor = confirmarContrasena,
					stringResource(R.string.confirmar_contrasena_campo),
					stringResource(R.string.contrasena_confirmar_ph),
					iconoContrasena,
					mensajeError = errorContrasenaConfirmar
				)
				Spacer(modifier = Modifier.height(15.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.btn_crear_cuenta_text),
					habilitarBoton = bHabilitarBoton
				) {
					keyboardController?.hide()
					scope.launch {
						createAccount(
							navegarALogin,
							context,
							userModel,
							identificacion.value,
							nombreCompleto.value,
							correo.value,
							telefono.value,
							tipoUsuario.value,
							password.value,
							confirmarContrasena.value,
							errorIdentificacion,
							errorNombreCompleto,
							errorCorreo,
							errorTelefono,
							errorFechaNacimiento,
							errorContrasena,
							errorContrasenaConfirmar
						
						)
					}
				}
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = stringResource(R.string.tiene_cuenta_secundario)
					)
					MostrarTextButton(
						sLabel = stringResource(R.string.btn_iniciar_sesion_text),
						onClick = { navegarALogin() },
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
	userModel: UserViewModel,
	identificacion: String,
	nombreCompleto: String,
	correo: String,
	telefono: String,
	tipoUsuario: String,
	password: String,
	confirmarContrasena: String,
	errorIdentificacion: MutableState<String?>,
	errorNombreCompleto: MutableState<String?>,
	errorCorreo: MutableState<String?>,
	errorTelefono: MutableState<String?>,
	errorFechaNacimiento: MutableState<String?>,
	errorContrasena: MutableState<String?>,
	errorContrasenaConfirmar: MutableState<String?>
) {
	var valido = true
	
	//Identificacion
	if (identificacion.trim().isNullOrEmpty()) {
		valido = false
	} else {
		val regex = Regex("^[0-9]+$")
		if (!identificacion.matches(regex)) {
			errorIdentificacion.value = "Solo se permiten dígitos numéricos"
			valido = false
		}
	}
	//Nombre
	if (nombreCompleto.trim().isNullOrEmpty()) {
		valido = false
	}
	if (correo.trim().isNullOrEmpty()) {
		valido = false
	} else {
		if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()) {
			errorCorreo.value = "Correo inválido"
			valido = false
		}
	}
	if (telefono.trim().isNullOrEmpty()) {
		valido = false
	} else {
		val regex = Regex("^\\d{4}-\\d{4}$")
		if (!telefono.matches(regex)) {
			errorTelefono.value = "Formato correcto: ####-####"
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
	if (confirmarContrasena.trim().isNullOrEmpty()) {
		valido = false
	} else {
		if (confirmarContrasena.trim().length < 6) {
			errorContrasenaConfirmar.value = "La contraseña debe tener al menos 6 caracteres"
			valido = false
		}
		if (confirmarContrasena != password) {
			errorContrasenaConfirmar.value = "La confirmación no coincide con la contraseña"
			valido = false
		}
	}
	
	
	if (valido) {
		when (val result = userModel.createUserWithEmailAndPassword(
			identificacion,
			nombreCompleto,
			correo,
			telefono,
			tipoUsuario,
			password
		)) {
			is AuthRes.Success -> {
				Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
				navigateToLogin()
			}
			
			is AuthRes.Error -> {
				Toast.makeText(
					context,
					"Error: ${result.errorMessage}",
					Toast.LENGTH_SHORT
				).show()
			}
		}
	}
}


