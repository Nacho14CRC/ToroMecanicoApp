package com.example.toromecanicoapp.ui.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.components.MostrarButton
import com.example.toromecanicoapp.ui.components.MostrarOutlinedTextField
import com.example.toromecanicoapp.ui.components.MostrarPasswordTextField
import com.example.toromecanicoapp.ui.components.MostrarTextButton
import com.example.toromecanicoapp.viewModel.LoginViewModel

@Composable
fun MostrarLoginScreen(modelo: LoginViewModel = viewModel()) {
	val LoginUiState by modelo.uiState.collectAsState()
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	
	val iconoUsuario = painterResource(id = R.drawable.ic_account_circle)
	val iconoContrasena = painterResource(id = R.drawable.ic_lock)
	
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
				MostrarOutlinedTextField(
					stringResource(R.string.login_usuario),
					stringResource(R.string.login_usuario_ph),
					modelo.sUsuario,
					iconoUsuario,
					true,
					LoginUiState.bErrorIngreso,
					{ modelo.ModificarUsuarioIngresado(it) }
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					stringResource(R.string.login_contrasena),
					stringResource(R.string.empty_string),
					modelo.sContrasena,
					iconoContrasena,
					LoginUiState.bErrorIngreso,
					{ modelo.ModificarContrasenaIngresado(it) }
				)
				MostrarTextButton(
					sLabel = stringResource(R.string.olvido_contrasena_text),
					onClick = { modelo.RestablecerContrasena() },
					modifier = Modifier.align(Alignment.End)
				)
				Spacer(modifier = Modifier.height(40.dp))
				MostrarButton(
					sLabel = stringResource(R.string.login_button_text),
					onClick = { modelo.Login() })
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
						onClick = { modelo.CrearCuenta() },
						modifier = Modifier
					)
				}
			}
		}
	}
}

