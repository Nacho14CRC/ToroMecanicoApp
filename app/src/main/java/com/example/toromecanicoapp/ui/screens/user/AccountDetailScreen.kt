package com.example.toromecanicoapp.ui.screens.user

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.home.InicioDestino
import com.example.toromecanicoapp.viewmodels.UserViewModel

object MiCuentaDestino : Destinos {
	override val ruta = "detalle_cuenta"
	override val tituloRecurso = R.string.identificacion_usuario
	override val descripcionIcono = "Cuenta"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MiCuentaScreen(
	navegarALogin: () -> Unit,
	navegarAInicio: () -> Unit,
	navegarACitas: () -> Unit,
	navegarAMiCuenta: () -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier = Modifier,
	modelo: UserViewModel
) {
	
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(InicioDestino.tituloRecurso),
				canNavigateBack = false,
				navegarALogin = navegarALogin,
				modelo = modelo,
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
		Column(
			modifier = Modifier.padding(innerPadding)
		) {
			Text(
				text = "Detalle cuenta",
				style = MaterialTheme.typography.displayMedium,
			)
		}
	}
}