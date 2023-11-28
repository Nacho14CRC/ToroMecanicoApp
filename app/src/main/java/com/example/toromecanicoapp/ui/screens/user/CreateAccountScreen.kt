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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toromecanicoapp.FormatearDate
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedEmailTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextPhoneField
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
	val fechaNacimiento = rememberSaveable {
		mutableStateOf("")
	}
	
	val valido = remember(correo.value, password.value) {
		correo.value.trim().isNotEmpty() &&
				password.value.trim().isNotEmpty()
	}
	val scope = rememberCoroutineScope()
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoIdentificacion = painterResource(id = R.drawable.ic_person)
	val iconoNombreCompleto = painterResource(id = R.drawable.ic_person)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
	val iconoTelefono = painterResource(id = R.drawable.ic_phone)
	val iconoCalendario = painterResource(id = R.drawable.ic_calendar)
	val iconoUsuario = painterResource(id = R.drawable.ic_account_circle)
	val iconoContrasena = painterResource(id = R.drawable.ic_lock)
	val keyboardController = LocalSoftwareKeyboardController.current
	var openDatePicker by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
	//Combo
	val lstTipoCliente = listOf("Cliente", "Mecánico")
	var expandirComboTipo by remember { mutableStateOf(false) }
	val tipoUsuario = rememberSaveable {
		mutableStateOf(lstTipoCliente[0])
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
					text = identificacion,
					stringResource(R.string.identificacion_usuario),
					stringResource(R.string.identificacion_usuario_ph),
					iconoIdentificacion,
					true
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedTextField(
					text = nombreCompleto,
					stringResource(R.string.nombre_usuario),
					stringResource(R.string.nombre_usuario_ph),
					iconoNombreCompleto,
					true
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedEmailTextField(
					valor = correo,
					label = stringResource(R.string.correo_usuario),
					placeholder = stringResource(R.string.correo_usuario_ph),
					leadingIcon = iconoCorreo,
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
						MostrarOutlinedTextPhoneField(
							text = telefono,
							stringResource(R.string.telefono_usuario),
							stringResource(R.string.empty_string),
							iconoTelefono,
							true
						)
					}
					Box(
						modifier = Modifier
							.weight(1f)
					) {
						OutlinedTextField(
							value = fechaNacimiento.value,
							singleLine = true,
							onValueChange = {
								fechaNacimiento.value = it
							},
							label = { Text(text = stringResource(R.string.texto_fecha_nacimiento)) },
							placeholder = { Text(text = stringResource(R.string.empty_string)) },
							leadingIcon = {
								IconButton(
									onClick = { openDatePicker = true }
								) {
									Icon(
										painter = iconoCalendario,
										contentDescription = null
									)
								}
							},
						)
						
						if (openDatePicker) {
							Dialog(onDismissRequest = { openDatePicker = false }) {
								DatePickerDialog(
									onDismissRequest = {
										openDatePicker = false
									},
									confirmButton = {
										TextButton(
											onClick = {
												openDatePicker = false
												datePickerState.selectedDateMillis?.let { selectedDateMillis ->
													val formattedDate =
														FormatearDate(selectedDateMillis)
													fechaNacimiento.value = formattedDate
												}
											},
											enabled = datePickerState.selectedDateMillis != null
										) {
											Text("Aceptar")
										}
									},
									dismissButton = {
										TextButton(
											onClick = {
												openDatePicker = false
											}
										) {
											Text("Cancelar")
										}
									}
								) {
									DatePicker(state = datePickerState)
								}
							}
						}
					}
				}
				Spacer(modifier = Modifier.height(16.dp))
				ExposedDropdownMenuBox(
					expanded = expandirComboTipo,
					onExpandedChange = { expandirComboTipo = !expandirComboTipo }
				) {
					TextField(
						modifier = Modifier.menuAnchor().fillMaxWidth(),
						readOnly = true,
						value = tipoUsuario.value,
						onValueChange = { },
						label = { Text("Tipo Usuario") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirComboTipo) },
						leadingIcon = {
							Icon(
								painter = iconoUsuario,
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
					stringResource(R.string.login_contrasena),
					stringResource(R.string.contrasena_ingresar),
					iconoContrasena,
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					valor = confirmarContrasena,
					stringResource(R.string.confirmar_contrasena),
					stringResource(R.string.contrasena_confirmar_ingresar),
					iconoContrasena
				)
				Spacer(modifier = Modifier.height(40.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.btn_crear_cuenta),
					inputValido = valido
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
							fechaNacimiento.value
						
						)
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
	fechaNacimiento: String
) {
	when (val result = userModel.createUserWithEmailAndPassword(
		identificacion,
		nombreCompleto,
		correo,
		telefono,
		tipoUsuario,
		password,
		fechaNacimiento
	)) {
		is AuthRes.Success -> {
			Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
			navigateToLogin()
		}
		
		is AuthRes.Error -> {
			Toast.makeText(
				context,
				"Error createAccount: ${result.errorMessage}",
				Toast.LENGTH_SHORT
			).show()
		}
	}
}


