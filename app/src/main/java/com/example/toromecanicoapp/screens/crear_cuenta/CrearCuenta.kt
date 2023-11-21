package com.example.toromecanicoapp.screens.crear_cuenta

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.toromecanicoapp.screens.login.LoginScreenViewModel

@Composable
fun CrearCuentaScreen(modelo: LoginScreenViewModel = viewModel(), navController: NavHostController) {
	/*val LoginUiState by modelo.uiState.collectAsState()
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoNombreCompleto = painterResource(id = R.drawable.ic_person)
	val iconoIdentificacion = painterResource(id = R.drawable.ic_person)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
	val iconoContrasena = painterResource(id = R.drawable.ic_lock)
	val iconoTelefono = painterResource(id = R.drawable.ic_phone)
	val iconoCalendario = painterResource(id = R.drawable.ic_calendar)
	
	
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
					stringResource(R.string.nombre_usuario),
					stringResource(R.string.nombre_usuario_ph),
					modelo.sNombreCompleto,
					iconoNombreCompleto,
					true,
					LoginUiState.bErrorRegistro,
					{ modelo.ModificarNombreCompleto(it) }
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedTextField(
					stringResource(R.string.identificacion_usuario),
					stringResource(R.string.identificacion_usuario_ph),
					modelo.sIdentificacion,
					iconoIdentificacion,
					true,
					LoginUiState.bErrorRegistro,
					{ modelo.ModificarIdentificacion(it) }
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarOutlinedEmailTextField(
					stringResource(R.string.correo_usuario),
					stringResource(R.string.correo_usuario_ph),
					modelo.sCorreo,
					iconoCorreo,
					true,
					LoginUiState.bErrorRegistro,
					{ modelo.ModificarCorreo(it) }
				)
				Spacer(modifier = Modifier.height(16.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Box(
						modifier = Modifier
							.weight(1f).padding(end = 8.dp)
					) {
						MostrarOutlinedTextPhoneField(
							stringResource(R.string.telefono_usuario),
							stringResource(R.string.telefono_usuario_ph),
							modelo.sTelefono,
							iconoTelefono,
							true,
							LoginUiState.bErrorIngreso,
							{ modelo.ModificarUsuarioIngresado(it) },
							modifier = Modifier
						)
					}
					Box(
						modifier = Modifier
							.weight(1f)
					) {
					}
				}
				Spacer(modifier = Modifier.height(40.dp))
				MostrarPasswordTextField(
					stringResource(R.string.login_contrasena),
					stringResource(R.string.empty_string),
					modelo.sContrasena,
					iconoContrasena,
					LoginUiState.bErrorIngreso,
					{ modelo.ModificarContrasenaIngresado(it) }
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					stringResource(R.string.confirmar_contrasena),
					stringResource(R.string.empty_string),
					modelo.sConfirmarContrasena,
					iconoContrasena,
					LoginUiState.bErrorIngreso,
					{ modelo.ModificarConfirmarContrasena(it)  }
				)
				Spacer(modifier = Modifier.height(40.dp))
				MostrarButton(
					sLabel = stringResource(R.string.btn_crear_cuenta),
					onClick = { modelo.CrearCuenta() })
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
						onClick = { modelo.NavegarACrearCuenta(navController) },
						modifier = Modifier
					)
				}
			}
		}
		
	}*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateInputSample() {
		val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
		DatePicker(state = state, modifier = Modifier.padding(16.dp))
}