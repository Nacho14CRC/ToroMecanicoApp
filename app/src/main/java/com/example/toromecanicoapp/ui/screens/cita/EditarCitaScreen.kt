package com.example.toromecanicoapp.ui.screens.cita

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object EditarCitaDestino : Destinos {
	override val ruta = "cita_edit"
	override val tituloRecurso = R.string.edit_cita_title
	override val descripcionIcono = ""
	const val citaIdArg = "citaId"
	val rutaArgumentos = "$ruta/{$citaIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCitaScreen(
	id: String,
	navegarALogin: () -> Unit,
	navegarAnterior: () -> Unit,
	navegarAtras: () -> Unit,
	usuarioModel: UserViewModel,
	modifier: Modifier = Modifier,
	citaModel: CitaViewModel = CitaViewModel()
) {
	val citaDetalle by citaModel.GetCitaByDocument(id).collectAsState(initial = null)
	val coroutineScope = rememberCoroutineScope()
	val observaciones = rememberSaveable { mutableStateOf("PRUEB") }
	
	val valido = remember(observaciones.value) {
		observaciones.value.trim().isNotEmpty()
	}
	
	val context = LocalContext.current
	
	Scaffold(
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(EditarCitaDestino.tituloRecurso),
				canNavigateBack = true,
				navegarALogin = navegarALogin,
				modelo = usuarioModel,
				modifier = modifier,
				navegarAtras = navegarAtras
			)
		}
	) { innerPadding ->
		EditarCitaBody(
			observaciones, valido, navegarAnterior, context, citaModel, id,
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun EditarCitaBody(
	observaciones: MutableState<String>,
	valido: Boolean,
	navegarAnterior: () -> Unit,
	context: Context,
	citaModel: CitaViewModel,
	id: String?,
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
				EditarCita(navegarAnterior, context, citaModel, id, observaciones.value)
			}
		}
	}
}

private suspend fun EditarCita(
	navegarAnterior: () -> Unit,
	context: Context,
	modelo: CitaViewModel,
	id: String?,
	observaciones: String?,
) {
	/*when (val result = modelo.EditarCita(id = id, observaciones = observaciones)) {
		is AuthRes.Success<*> -> {
			navegarAnterior()
		}
		
		is AuthRes.Error -> {
			Toast.makeText(
				context,
				"Error al editar la cita: ${result.errorMessage}",
				Toast.LENGTH_SHORT
			)
				.show()
		}
	}*/
}
