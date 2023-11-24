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
import com.example.toromecanicoapp.ui.navegation.Destinations
import com.example.toromecanicoapp.ui.screens.home.HomeDestination
import com.example.toromecanicoapp.viewmodels.UserViewModel

object CuentaDetailDestination : Destinations {
	override val route = "detalle_cuenta"
	override val titleRes = R.string.identificacion_usuario
	override val desIcono = "Cuenta"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowCuentaScreen(
	navigateToLogin: () -> Unit,
	navigateToHome: () -> Unit,
	navigateToCitas: () -> Unit,
	navigateToCuenta: () -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier = Modifier,
	modelo: UserViewModel
) {
	
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(HomeDestination.titleRes),
				canNavigateBack = false,
				navigateToLogin = navigateToLogin,
				modelo = modelo,
				modifier = modifier
			)
		},
		bottomBar = {
			ToroMecanicoBottomAppBar(
				navigateToHome,
				navigateToCitas,
				navigateToCuenta,
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