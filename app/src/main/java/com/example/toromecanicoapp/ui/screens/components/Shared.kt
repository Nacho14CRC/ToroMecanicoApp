package com.example.toromecanicoapp.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.theme.Shapes
import com.example.toromecanicoapp.ui.theme.Typography

/*Inputs*/
@Composable
fun MostrarOutlinedTextField(
	text: MutableState<String>,
	label: String,
	placeholder: String,
	leadingIcon: Painter,
	singleLine: Boolean
) {
	OutlinedTextField(
		value = text.value,
		onValueChange = { text.value = it },
		label = { Text(text = label) },
		placeholder = { Text(placeholder) },
		colors = TextFieldDefaults.colors(
			focusedContainerColor = MaterialTheme.colorScheme.surface,
			unfocusedContainerColor = MaterialTheme.colorScheme.surface,
			disabledContainerColor = MaterialTheme.colorScheme.surface,
		),
		leadingIcon = {
			Icon(
				painter = leadingIcon,
				contentDescription = null
			)
		},
		singleLine = singleLine,
		modifier = Modifier.fillMaxWidth(),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Next
		)
	)
}

@Composable
fun MostrarOutlinedTextArea(
	text: MutableState<String>,
	label: String,
	placeholder: String,
	leadingIcon: Painter
) {
	OutlinedTextField(
		value = text.value,
		onValueChange = { text.value = it },
		label = { Text(text = label) },
		placeholder = { Text(placeholder) },
		colors = TextFieldDefaults.colors(
			focusedContainerColor = MaterialTheme.colorScheme.surface,
			unfocusedContainerColor = MaterialTheme.colorScheme.surface,
			disabledContainerColor = MaterialTheme.colorScheme.surface,
		),
		leadingIcon = {
			Icon(
				painter = leadingIcon,
				contentDescription = null
			)
		},
		singleLine = false,
		modifier = Modifier
			.fillMaxWidth()
			.heightIn(min = 200.dp),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done
		)
	)
}

@Composable
fun MostrarOutlinedEmailTextField(
	valor: MutableState<String>,
	label: String,
	placeholder: String,
	leadingIcon: Painter,
	singleLine: Boolean,
	mensajeError: MutableState<String?>,
) {
	OutlinedTextField(
		value = valor.value,
		onValueChange = {
			valor.value = it
			mensajeError.value = null
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Email,
			imeAction = ImeAction.Next
		),
		label = { Text(text = label) },
		placeholder = { Text(placeholder) },
		colors = TextFieldDefaults.colors(
			focusedContainerColor = MaterialTheme.colorScheme.surface,
			unfocusedContainerColor = MaterialTheme.colorScheme.surface,
			disabledContainerColor = MaterialTheme.colorScheme.surface,
		),
		leadingIcon = {
			Icon(
				painter = leadingIcon,
				contentDescription = null
			)
		},
		singleLine = singleLine,
		modifier = Modifier.fillMaxWidth()
	)
	mensajeError.value?.let { message ->
		if (message.isNotEmpty()) {
			Text(
				text = message,
				color = Color.Red,
				fontSize = 12.sp,
				modifier = Modifier
					.padding(top = 4.dp, start = 16.dp)
					.fillMaxWidth()
					.wrapContentHeight()
			)
		}
	}
}


@Composable
fun MostrarPasswordTextField(
	valor: MutableState<String>,
	label: String,
	placeholder: String,
	leadingIcon: Painter,
	mensajeError: MutableState<String?>,
) {
	var contrasenaVisible by remember { mutableStateOf(false) }
	
	OutlinedTextField(
		value = valor.value,
		onValueChange = {
			valor.value = it
			mensajeError.value = null
		},
		label = { Text(text = label) },
		placeholder = { Text(placeholder) },
		colors = TextFieldDefaults.colors(
			focusedContainerColor = MaterialTheme.colorScheme.surface,
			unfocusedContainerColor = MaterialTheme.colorScheme.surface,
			disabledContainerColor = MaterialTheme.colorScheme.surface,
		),
		leadingIcon = {
			Icon(
				painter = leadingIcon,
				contentDescription = null
			)
		},
		singleLine = true,
		visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
		keyboardOptions = KeyboardOptions.Default.copy(
			keyboardType = KeyboardType.Password,
			imeAction = ImeAction.Next
		),
		modifier = Modifier.fillMaxWidth(),
		trailingIcon = {
			IconButton(
				onClick = { contrasenaVisible = !contrasenaVisible }
			) {
				Icon(
					painter = if (contrasenaVisible) painterResource(id = R.drawable.ic_eye_off) else painterResource(
						id = R.drawable.ic_eye
					),
					contentDescription = null
				)
			}
		}
	)
	mensajeError.value?.let { message ->
		if (message.isNotEmpty()) {
			Text(
				text = message,
				color = Color.Red,
				fontSize = 12.sp,
				modifier = Modifier
					.padding(top = 4.dp, start = 16.dp)
					.fillMaxWidth()
					.wrapContentHeight()
			)
		}
	}
}

/*Botones*/
@Composable
fun MostrarTextButton(sLabel: String, onClick: () -> Unit, modifier: Modifier) {
	TextButton(
		onClick = { onClick() },
		modifier = modifier
	) {
		Text(
			text = sLabel,
			textDecoration = TextDecoration.Underline,
			style = MaterialTheme.typography.labelSmall,
		)
	}
}

@Composable
fun MostrarSubmitButton(
	sLabel: String,
	habilitarBoton: Boolean,
	onClick: () -> Unit
) {
	Button(
		modifier = Modifier
			.fillMaxWidth()
			.height(48.dp),
		onClick = { onClick() },
		shape = Shapes.extraLarge,
		enabled = habilitarBoton
	) {
		Text(
			text = sLabel,
			style = TextStyle(
				fontFamily = Typography.displayLarge.fontFamily
			)
		)
	}
}

@Composable
fun MostrarOutlinedTextPhoneField(
	text: MutableState<String>,
	label: String,
	placeholder: String,
	leadingIcon: Painter,
	singleLine: Boolean
) {
	OutlinedTextField(
		value = text.value,
		onValueChange = { text.value = it },
		label = { Text(text = label) },
		placeholder = { Text(placeholder) },
		colors = TextFieldDefaults.colors(
			focusedContainerColor = MaterialTheme.colorScheme.surface,
			unfocusedContainerColor = MaterialTheme.colorScheme.surface,
			disabledContainerColor = MaterialTheme.colorScheme.surface,
		),
		leadingIcon = {
			Icon(
				painter = leadingIcon,
				contentDescription = null
			)
		},
		singleLine = singleLine,
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Phone,
			imeAction = ImeAction.Next
		),
	)
}

@Composable
fun RadioGroupSample() {
	val radioOptions =
		listOf("General", "Eléctrico", "Enderezado y pintura", "Atención de seguros")
	val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
	Column(Modifier.selectableGroup()) {
		Text(text = "Seleccione un servicio:")
		radioOptions.forEach { text ->
			Row(
				Modifier
					.fillMaxWidth()
					.height(56.dp)
					.selectable(
						selected = (text == selectedOption),
						onClick = { onOptionSelected(text) },
						role = Role.RadioButton
					)
					.padding(horizontal = 16.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				RadioButton(
					selected = (text == selectedOption),
					onClick = null
				)
				Text(
					text = text,
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier.padding(start = 16.dp)
				)
			}
		}
	}
}

@Composable
fun TriStateCheckboxSample() {
	Column {
		val (general, onStateGeneral) = remember { mutableStateOf(false) }
		val (electrico, onStateElectrico) = remember { mutableStateOf(false) }
		val (enderezadoPintura, onStateEnderezadoPintura) = remember { mutableStateOf(false) }
		val (seguro, onStateSeguro) = remember { mutableStateOf(false) }
		val parentState = remember(general, electrico, enderezadoPintura, seguro) {
			if (general && electrico && enderezadoPintura && seguro) ToggleableState.On
			else if (!general && !electrico && !enderezadoPintura && !seguro) ToggleableState.Off
			else ToggleableState.Indeterminate
		}
		val textoPrincipal = when {
			parentState == ToggleableState.On -> "Quitar selección"
			parentState == ToggleableState.Off -> "Seleccionar todos los servicios"
			else -> "Seleccionar los servicios restantes"
		}
		
		val onParentClick = {
			val s = parentState != ToggleableState.On
			onStateGeneral(s)
			onStateElectrico(s)
			onStateEnderezadoPintura(s)
			onStateSeguro(s)
		}
		
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(8.dp)
		) {
			TriStateCheckbox(
				state = parentState,
				onClick = onParentClick,
			)
			Text(text = textoPrincipal)
		}
		
		Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Checkbox(general, onStateGeneral)
				Text(text = "General")
			}
			
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Checkbox(electrico, onStateElectrico)
				Text(text = "Eléctrico")
			}
			
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Checkbox(enderezadoPintura, onStateEnderezadoPintura)
				Text(text = "Enderezado y pintura")
			}
			
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Checkbox(seguro, onStateSeguro)
				Text(text = "Atención de seguros")
			}
		}
	}
}
