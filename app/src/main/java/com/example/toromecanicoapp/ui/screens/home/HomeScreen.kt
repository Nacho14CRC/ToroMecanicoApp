package com.example.toromecanicoapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.example.toromecanicoapp.viewmodels.UserViewModel

object HomeDestination : Destinations {
	override val route = "home"
	override val titleRes = R.string.app_name
	override val desIcono = "Home"
}

@Composable
fun ShowHomeScreen(
	navigateToLogin: () -> Unit,
	navigateToHome: () -> Unit,
	navigateToCitas: () -> Unit,
	navigateToCuenta: () -> Unit,
	currentDestination: NavDestination?,
	userModel: UserViewModel,
	modifier: Modifier = Modifier
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(HomeDestination.titleRes),
				canNavigateBack = false,
				navigateToLogin = navigateToLogin,
				modelo = userModel,
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
				text = "Home",
				style = MaterialTheme.typography.displayMedium,
			)
		}
	}
}

