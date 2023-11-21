package com.example.toromecanicoapp.screens.olvido_contrasena

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.toromecanicoapp.R

@Composable
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
}
