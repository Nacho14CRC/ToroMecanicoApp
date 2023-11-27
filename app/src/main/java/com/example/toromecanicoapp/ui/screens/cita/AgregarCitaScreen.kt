package com.example.toromecanicoapp.ui.screens.cita

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextField
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object AgregarCitaDestino : Destinos {
	override val ruta = "cita_entry"
	override val tituloRecurso = R.string.agregar_cita_titulo
	override val descripcionIcono = ""
}


@Composable
fun AgregarCitaScreen(
	navegarALogin: () -> Unit,
	navegarAnterior: () -> Unit,
	navegarAtras: () -> Unit,
	modifier: Modifier = Modifier,
	userModel: UserViewModel,
	citaModel: CitaViewModel = CitaViewModel()
) {
	val observaciones = rememberSaveable { mutableStateOf("") }
	val valido = remember(observaciones.value) {
		observaciones.value.trim().isNotEmpty()
	}
	
	val context = LocalContext.current
	var userId = userModel.getCurrentUser()?.uid
	
	Scaffold(
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(AgregarCitaDestino.tituloRecurso),
				canNavigateBack = true,
				navegarALogin = navegarALogin,
				modelo = userModel,
				modifier = modifier,
				navegarAtras = navegarAtras
			)
		}
	) { innerPadding ->
		AgregarCitaBody(
			observaciones, valido, navegarAnterior, context, citaModel, userId,
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun AgregarCitaBody(
	observaciones: MutableState<String>,
	valido: Boolean,
	navigateBack: () -> Unit,
	context: Context,
	citaModel: CitaViewModel,
	userId: String?,
	modifier: Modifier = Modifier
) {
	val iconoObservaciones = painterResource(id = R.drawable.ic_calendar)
	val keyboardController = LocalSoftwareKeyboardController.current
	val scope = rememberCoroutineScope()
	
	Column(
		modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
	) {
		MostrarOutlinedTextField(
			text = observaciones,
			label = stringResource(R.string.cita_observaciones),
			placeholder = stringResource(R.string.cita_observaciones_ph),
			leadingIcon = iconoObservaciones,
			singleLine = true
		)
		MostrarSubmitButton(
			sLabel = stringResource(R.string.guardar_button_text),
			inputValido = valido
		) {
			keyboardController?.hide()
			scope.launch {
				AgregarCita(navigateBack, context, citaModel, userId, observaciones.value)
			}
		}
	}
}

private suspend fun AgregarCita(
	navigateBack: () -> Unit,
	context: Context,
	modelo: CitaViewModel,
	userId: String?,
	observaciones: String,
) {
	when (val result = modelo.AgregarCita(userId = userId, observaciones = observaciones)) {
		is AuthRes.Success<*> -> {
			navigateBack()
		}
		
		is AuthRes.Error -> {
			Toast.makeText(
				context,
				"Error al agregar la cita: ${result.errorMessage}",
				Toast.LENGTH_SHORT
			)
				.show()
		}
	}
}
