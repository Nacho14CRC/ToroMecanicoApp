package com.example.toromecanicoapp.ui.screens.cita

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.ui.navegation.Destinations
import com.example.toromecanicoapp.ui.screens.home.HomeDestination
import com.example.toromecanicoapp.viewmodels.UserViewModel

object CitaEntryDestination : Destinations {
	override val route = "cita_entry"
	override val titleRes = R.string.identificacion_usuario
	override val desIcono = ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaEntryScreen(
	navigateToLogin: () -> Unit,
	navigateBack: () -> Unit,
	onNavigateUp: () -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier = Modifier,
	modelo: UserViewModel
) {
	Scaffold(
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(HomeDestination.titleRes),
				canNavigateBack = true,
				navigateToLogin = navigateToLogin,
				modelo = modelo,
				modifier = modifier,
				navigateUp = onNavigateUp
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(innerPadding)
		) {
			Text(
				text = "Desde agregar cita",
				style = MaterialTheme.typography.displayMedium,
			)
		}
	}
}