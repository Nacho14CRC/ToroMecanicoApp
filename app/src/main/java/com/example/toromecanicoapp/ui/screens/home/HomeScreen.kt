package com.example.toromecanicoapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.viewmodels.UserViewModel

object InicioDestino : Destinos {
	override val ruta = "home"
	override val tituloRecurso = R.string.app_name
	override val descripcionIcono = "Home"
}

@Composable
fun InicioScreen(
	navegarALogin: () -> Unit,
	navegarAInicio: () -> Unit,
	navegarACitas: () -> Unit,
	navegarAMiCuenta: () -> Unit,
	currentDestination: NavDestination?,
	usuarioModelo: UserViewModel,
	modifier: Modifier = Modifier
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(InicioDestino.tituloRecurso),
				canNavigateBack = false,
				navegarALogin = navegarALogin,
				modelo = usuarioModelo,
				modifier = modifier
			)
		},
		bottomBar = {
			ToroMecanicoBottomAppBar(
				navegarAInicio,
				navegarACitas,
				navegarAMiCuenta,
				currentDestination
			)
		}
	) { innerPadding ->
		HomeBody(
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun HomeBody(
	modifier: Modifier = Modifier
) {
	
	Column(
		modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
	) {
		Text(text = "Home")
	}
}
