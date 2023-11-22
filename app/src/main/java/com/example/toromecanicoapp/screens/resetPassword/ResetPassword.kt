package com.example.toromecanicoapp.screens.resetPassword

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.screens.components.MostrarOutlinedEmailTextField
import com.example.toromecanicoapp.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.screens.components.MostrarTextButton
import com.example.toromecanicoapp.viewmodels.UserViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowForgotPassword(navController: NavHostController, modelo: UserViewModel = viewModel()) {
	val email = rememberSaveable {
		mutableStateOf("")
	}
	val valido = remember(email.value) {
		email.value.trim().isNotEmpty()
	}
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	val iconoCorreo = painterResource(id = R.drawable.ic_email)
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
					text = stringResource(R.string.restablecer_contrasena),
					style = MaterialTheme.typography.displayMedium,
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = stringResource(R.string.restablecer_contrasena_text),
					style = MaterialTheme.typography.labelSmall,
				)
				
				Spacer(modifier = Modifier.height(40.dp))
				
				MostrarOutlinedEmailTextField(
					emailState = email,
					label = stringResource(R.string.correo_usuario),
					placeholder = stringResource(R.string.correo_usuario_ph),
					leadingIcon = iconoCorreo,
					singleLine = true
				)
				Spacer(modifier = Modifier.height(16.dp))
				MostrarSubmitButton(
					sLabel = stringResource(R.string.btn_restablecer),
					inputValido = valido
				) {
					keyboardController?.hide()
					modelo.RestablecerContrasena(email.value)
				}
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					MostrarTextButton(
						sLabel = stringResource(R.string.login_button_text),
						onClick = { modelo.NavegarALogin(navController) },
						modifier = Modifier
					)
				}
			}
		}
		
	}
}

/*@Composable
fun OlvidoContrasenaScreen(navController: NavHostController) {
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	
	Column(
		modifier = Modifier.fillMaxSize().background(Color.Blue),
	) {
		Text(
			text = stringResource(R.string.restablecer_contrasena),
			style = MaterialTheme.typography.displayMedium,
		)
	}
}*/
