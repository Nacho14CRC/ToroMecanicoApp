package com.example.toromecanicoapp.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.components.MostrarOutlinedEmailTextField
import com.example.toromecanicoapp.ui.components.MostrarPasswordTextField
import com.example.toromecanicoapp.ui.components.MostrarSubmitButton

/*@Composable
fun MostrarLoginScreen(navController: NavController){
	Text(text = "Login")
}*/


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MostrarLoginScreen(navController: NavHostController) {
	
	val email = rememberSaveable {
		mutableStateOf("")
	}
	val password = rememberSaveable {
		mutableStateOf("")
	}
	val valido = remember(email.value, password.value){
		email.value.trim().isNotEmpty() &&
				password.value.trim().isNotEmpty()
	}
	val keyboardController = LocalSoftwareKeyboardController.current
	
	//val LoginUiState by modelo.uiState.collectAsState()
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
				MostrarOutlinedEmailTextField(
					emailState = email,
					stringResource(R.string.correo_usuario),
					stringResource(R.string.correo_usuario_ph),
					iconoUsuario,
					true
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarPasswordTextField(
					passwordState = password,
					stringResource(R.string.login_contrasena),
					stringResource(R.string.empty_string),
					iconoContrasena
				)
				/*MostrarTextButton(
					sLabel = stringResource(R.string.olvido_contrasena_text),
					onClick = { modelo.NavegarARestablerContrasena(navController) },
					modifier = Modifier.align(Alignment.End)
				)*/
				Spacer(modifier = Modifier.height(40.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.login_button_text),
					inputValido = valido){
					keyboardController?.hide()
					Log.d("toroMecanicoApp", "Logueado")
				}
				/*Spacer(modifier = Modifier.height(8.dp))
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
						onClick = { modelo.NavegarACrearCuenta(navController) },
						modifier = Modifier
					)
				}*/
			}
		}
	}
}

