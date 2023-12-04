package com.example.toromecanicoapp.ui.screens.cita

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toromecanicoapp.FormatearDate
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.components.MostrarOutlinedTextArea
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.viewmodels.AuthRes
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object AgregarCitaDestino : Destinos {
	override val ruta = "cita_entry"
	override val tituloRecurso = R.string.agregar_cita_title
	override val descripcionIcono = ""
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarCitaScreen(
	navegarALogin: () -> Unit,
	navegarAnterior: () -> Unit,
	navegarAtras: () -> Unit,
	modifier: Modifier = Modifier,
	userModel: UserViewModel = viewModel(),
	citaModel: CitaViewModel = CitaViewModel()
) {
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
			navegarAnterior, context, citaModel, userId,
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun AgregarCitaBody(
	navigateBack: () -> Unit,
	context: Context,
	citaModel: CitaViewModel,
	userId: String?,
	modifier: Modifier = Modifier
) {
	//Generales
	val keyboardController = LocalSoftwareKeyboardController.current
	val scope = rememberCoroutineScope()
	var openDatePicker by remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
	val mediumPadding = dimensionResource(R.dimen.padding_medium)
	//Listas
	//val lstMecanicos = listOf("", "Alonso","Meca2")
	var lstMecanicos by remember { mutableStateOf<List<String>>(emptyList()) }
	LaunchedEffect(Unit) {
		citaModel.ObtenerNombresMecanicos().collect { nombres ->
			lstMecanicos = listOf("") + nombres
		}
	}
	
	//Iconos
	val iconoObservaciones = painterResource(id = R.drawable.ic_observaciones)
	val iconoCalendario = painterResource(id = R.drawable.ic_calendar)
	val iconoMecanico = painterResource(id = R.drawable.ic_mecanico)
	//Campos
	val fechaCita = rememberSaveable {
		mutableStateOf("")
	}
	val observaciones = rememberSaveable { mutableStateOf("") }
	var expandirComboMecanico by remember { mutableStateOf(false) }
	val comboMecanico = rememberSaveable {
		mutableStateOf(lstMecanicos.getOrNull(0) ?: "")
	}
	val errorFecha = remember { mutableStateOf<String?>(null) }
	
	val bHabilitarBoton = remember(
		fechaCita.value,
		comboMecanico.value,
		observaciones.value
	) {
		fechaCita.value.trim().isNotEmpty() &&
				comboMecanico.value.trim().isNotEmpty() &&
				observaciones.value.trim().isNotEmpty()
	}
	
	Column(
		modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
	) {
		OutlinedTextField(
			value = fechaCita.value,
			singleLine = true,
			onValueChange = {
				fechaCita.value = it
				errorFecha.value = null
			},
			label = { Text(text = stringResource(R.string.fecha_cita_campo)) },
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
		errorFecha.value?.let { message ->
			if (message.isNotEmpty()) {
				Text(
					text = message,
					color = Color.Red,
					fontSize = 12.sp,
					modifier = Modifier
						.padding(start = 16.dp)
						.wrapContentHeight()
				)
			}
		}
		
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
		Spacer(modifier = Modifier.height(16.dp))
		Row(
			modifier = Modifier
		) {
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
							painter = iconoMecanico,
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
		}
		Spacer(modifier = Modifier.height(16.dp))
		Row(
			modifier = Modifier
		) {
			MostrarOutlinedTextArea(
				text = observaciones,
				label = stringResource(R.string.cita_observaciones_campo),
				placeholder = stringResource(R.string.cita_observaciones_ph),
				leadingIcon = iconoObservaciones
			)
		}
		Spacer(modifier = Modifier.height(16.dp))
		Row(
			modifier = Modifier
		) {
			MostrarSubmitButton(
				sLabel = stringResource(R.string.btn_guardar_text),
				habilitarBoton = bHabilitarBoton
			) {
				keyboardController?.hide()
				scope.launch {
					AgregarCita(
						navigateBack,
						context,
						citaModel,
						userId,
						observaciones.value,
						fechaCita.value,
						comboMecanico.value,
						errorFecha
					)
				}
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
	fechaCita: String,
	comboMecanico: String,
	errorFecha: MutableState<String?>,
) {
	var valido = true
	val regex = Regex("^\\d{2}/\\d{2}/\\d{4}$")
	if (!fechaCita.matches(regex)) {
		errorFecha.value = "Formato valido: dd/mm/yyyy"
		valido = false
	}
	if (valido) {
		
		
		when (val result = modelo.AgregarCita(
			userId = userId,
			observaciones = observaciones,
			fechaCita,
			comboMecanico
		)) {
			is AuthRes.Success<*> -> {
				navigateBack()
			}
			
			is AuthRes.Error -> {
				Toast.makeText(
					context,
					"Error: ${result.errorMessage}",
					Toast.LENGTH_SHORT
				)
					.show()
			}
		}
	}
}
