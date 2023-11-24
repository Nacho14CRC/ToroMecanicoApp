package com.example.toromecanicoapp

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.ui.navegation.ToroMecanicoNavHost
import com.example.toromecanicoapp.ui.screens.account.CuentaDetailDestination
import com.example.toromecanicoapp.ui.screens.cita.CitasDestination
import com.example.toromecanicoapp.ui.screens.home.HomeDestination
import com.example.toromecanicoapp.viewmodels.UserViewModel

@Composable
fun ToroMecanicoApp(context: Context, navController: NavHostController = rememberNavController()) {
	ToroMecanicoNavHost(context, navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToroMecanicoTopAppBar(
	title: String,
	canNavigateBack: Boolean,
	navigateToLogin: () -> Unit,
	modelo: UserViewModel = viewModel(),
	modifier: Modifier,
	navigateUp: () -> Unit = {}
) {
	
	var showDialog by remember { mutableStateOf(false) }
	
	CenterAlignedTopAppBar(
		title = {
			Row(
				horizontalArrangement = Arrangement.Start,
				verticalAlignment = Alignment.CenterVertically
			) {
				
				Text(
					text = title,
					style = MaterialTheme.typography.displayMedium
				)
			}
		},
		modifier = modifier,
		actions = {
			IconButton(
				onClick = {
					showDialog = true
				}
			) {
				Icon(Icons.Outlined.Logout, contentDescription = null)
			}
		},
		navigationIcon = {
			if (canNavigateBack) {
				IconButton(onClick = navigateUp) {
					Icon(
						imageVector = Icons.Filled.ArrowBack,
						contentDescription = null
					)
				}
			}
		}
	)
	if (showDialog) {
		LogoutDialog(
			onConfirmLogout = {
				modelo.signOut()
				navigateToLogin()
				showDialog = false
			}, onDismiss = { showDialog = false })
	}
}

@Composable
fun ToroMecanicoBottomAppBar(
	navigateToHome: () -> Unit,
	navigateToCitas: () -> Unit,
	navigateToCuenta: () -> Unit,
	currentDestination: NavDestination?
) {
	NavigationBar {
		if (currentDestination != null) {
			NavigationBarItem(
				label = { Text(text = HomeDestination.desIcono) },
				icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Icons") },
				selected = currentDestination.hierarchy?.any {
					it.route == HomeDestination.route
				} == true,
				onClick = {
					navigateToHome()
				}
			)
		}
		
		if (currentDestination != null) {
			NavigationBarItem(
				label = { Text(text = CitasDestination.desIcono) },
				icon = {
					Icon(
						imageVector = Icons.Default.ViewTimeline,
						contentDescription = "Icons"
					)
				},
				selected = currentDestination.hierarchy?.any {
					it.route == CitasDestination.route
				} == true,
				onClick = {
					navigateToCitas()
				}
			)
		}
		
		
		if (currentDestination != null) {
			NavigationBarItem(
				label = { Text(text = CuentaDetailDestination.desIcono) },
				icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Icons") },
				selected = currentDestination.hierarchy?.any {
					it.route == CuentaDetailDestination.route
				} == true,
				onClick = {
					navigateToCuenta()
				}
			)
		}
	}
}

@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Cerrar sesión") },
		text = { Text("¿Estás seguro que deseas cerrar sesión?") },
		confirmButton = {
			Button(
				onClick = onConfirmLogout
			) {
				Text("Aceptar")
			}
		},
		dismissButton = {
			Button(
				onClick = onDismiss
			) {
				Text("Cancelar")
			}
		}
	)
}


