package com.example.toromecanicoapp.ui.screens.cita

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.toromecanicoapp.FormatearDate
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextArea
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.viewmodels.AuthRes
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
			citaDetalle, navegarAnterior, context, citaModel, id,
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun EditarCitaBody(
	citaDetalle: Cita?,
	navegarAnterior: () -> Unit,
	context: Context,
	citaModel: CitaViewModel,
	id: String?,
	modifier: Modifier = Modifier
) {
	
	//Generales
	val keyboardController = LocalSoftwareKeyboardController.current
	val scope = rememberCoroutineScope()
	var openDatePicker by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
	
	//Listas
	val lstMecanicos = listOf("Andrei", "Nacho")
	
	//Iconos
	val iconoObservaciones = painterResource(id = R.drawable.ic_calendar)
	val iconoCalendario = painterResource(id = R.drawable.ic_calendar)
	//Campos
	var observaciones = remember { mutableStateOf(citaDetalle?.observaciones ?: "Nulo") }
	var fechaCita = remember { mutableStateOf(citaDetalle?.fechaCita ?: "") }
	var comboMecanico = rememberSaveable {
		mutableStateOf(citaDetalle?.mecanico ?: "Nulo")
	}
	var expandirComboMecanico by remember { mutableStateOf(false) }
	
	//Validacion
	val valido = remember(observaciones.value) {
		observaciones.value.trim().isNotEmpty()
	}
	
	
	Column(
		modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
	) {
		if (citaDetalle != null) {
			//
			observaciones = remember { mutableStateOf(citaDetalle?.observaciones ?: "") }
			fechaCita = remember { mutableStateOf(citaDetalle?.fechaCita ?: "") }
			comboMecanico = remember { mutableStateOf(citaDetalle?.mecanico ?: "") }
			
			OutlinedTextField(
				value = fechaCita.value,
				singleLine = true,
				onValueChange = {
					fechaCita.value = it
				},
				label = { Text(text = stringResource(R.string.texto_fecha_cita)) },
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
				modifier = Modifier.fillMaxWidth()
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
										fechaCita.value = formattedDate
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
			//
			ExposedDropdownMenuBox(
				expanded = expandirComboMecanico,
				onExpandedChange = { expandirComboMecanico = !expandirComboMecanico }
			) {
				TextField(
					modifier = Modifier
						.menuAnchor()
						.fillMaxWidth(),
					readOnly = true,
					value = comboMecanico.value,
					onValueChange = { },
					label = { Text("Mecánico") },
					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirComboMecanico) },
					leadingIcon = {
						Icon(
							painter = iconoCalendario,
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
					expanded = expandirComboMecanico,
					onDismissRequest = { expandirComboMecanico = false },
				) {
					lstMecanicos.forEach { selectionOption ->
						DropdownMenuItem(
							text = { Text(selectionOption) },
							onClick = {
								comboMecanico.value = selectionOption
								expandirComboMecanico = false
							},
							contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
						)
					}
				}
			}
			//
			MostrarOutlinedTextArea(
				text = observaciones,
				label = stringResource(R.string.cita_observaciones),
				placeholder = stringResource(R.string.cita_observaciones_ph),
				leadingIcon = iconoObservaciones
			)
			MostrarSubmitButton(
				sLabel = stringResource(R.string.guardar_button_text),
				habilitarBoton = valido
			) {
				keyboardController?.hide()
				scope.launch {
					if (citaDetalle != null && id != null) {
						EditarCita(
							navegarAnterior,
							context,
							citaModel,
							id,
							citaDetalle.userId,
							fechaCita.value,
							comboMecanico.value,
							observaciones.value
						)
					}
				}
			}
		}
		
	}
}

private suspend fun EditarCita(
	navegarAnterior: () -> Unit,
	context: Context,
	modelo: CitaViewModel,
	id: String,
	userId: String,
	fechaCita: String,
	comboMecanico: String,
	observaciones: String,
) {
	when (val result = modelo.EditarCita(id = id, userId = userId,fechaCita=fechaCita, mecanico=comboMecanico, observaciones = observaciones)) {
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
	}
}
