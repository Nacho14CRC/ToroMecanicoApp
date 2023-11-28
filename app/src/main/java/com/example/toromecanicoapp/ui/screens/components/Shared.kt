package com.example.toromecanicoapp.ui.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.theme.Shapes
import com.example.toromecanicoapp.ui.theme.Typography
import java.util.Date

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
		modifier = Modifier.fillMaxWidth()
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
) {
	OutlinedTextField(
		value = valor.value,
		onValueChange = { valor.value = it },
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Email
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
}

@Composable
fun MostrarPasswordTextField(
	valor: MutableState<String>,
	label: String,
	placeholder: String,
	leadingIcon: Painter
) {
	var contrasenaVisible by remember { mutableStateOf(false) }
	
	OutlinedTextField(
		value = valor.value,
		onValueChange = { valor.value = it },
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
			keyboardType = KeyboardType.Password
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
	inputValido: Boolean,
	onClick: () -> Unit
) {
	Button(
		modifier = Modifier
			.fillMaxWidth()
			.height(48.dp),
		onClick = { onClick() },
		shape = Shapes.extraLarge,
		enabled = inputValido
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
		keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
	)
}


@Composable
fun MostrarOutlinedDateField(
	label: String,
	placeholder: String,
	text: Date?,
	leadingIcon: Painter,
	singleLine: Boolean,
	isError: Boolean,
	onValueChange: (String) -> Unit,
	modifier: Modifier
) {
//
}