package com.example.toromecanicoapp.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.navegation.BottomNavScreen
import com.example.toromecanicoapp.navegation.Screens
import com.example.toromecanicoapp.screens.citas.ShowCitasScreen
import com.example.toromecanicoapp.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navigation: NavController, modelo: UserViewModel = viewModel()) {
	val navController = rememberNavController()
	var showDialog by remember { mutableStateOf(false) }
	val context = LocalContext.current
	val onLogoutConfirmed: () -> Unit = {
		modelo.signOut()
		navigation.navigate(Screens.LoginScreen.name) {
			popUpTo(Screens.HomeScreen.name) {
				inclusive = true
			}
		}
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Row(
						horizontalArrangement = Arrangement.Start,
						verticalAlignment = Alignment.CenterVertically
					) {
						
						Text(
							text = stringResource(R.string.app_name),
							style = MaterialTheme.typography.displayMedium
						)
					}
				},
				colors = TopAppBarDefaults.mediumTopAppBarColors(),
				actions = {
					IconButton(
						onClick = {
							showDialog = true
						}
					) {
						Icon(Icons.Outlined.Logout, contentDescription = null)
					}
				}
			)
		},
		bottomBar = {
			BottomBar(navController = navController)
		}
	) { contentPadding ->
		Box(modifier = Modifier.padding(contentPadding)) {
			if (showDialog) {
				LogoutDialog(onConfirmLogout = {
					onLogoutConfirmed()
					showDialog = false
				}, onDismiss = { showDialog = false })
			}
			BottomNavGraph(navController = navController, context = context, modelo = modelo)
		}
	}
}

@Composable
fun BottomBar(navController: NavHostController) {
	val screens = listOf(BottomNavScreen.Home, BottomNavScreen.Citas, BottomNavScreen.Cuenta)
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination
	NavigationBar {
		screens.forEach { screens ->
			if (currentDestination != null) {
				AddItem(
					screens = screens,
					currentDestination = currentDestination,
					navController = navController
				)
			}
		}
	}
}

@Composable
fun RowScope.AddItem(
	screens: BottomNavScreen,
	currentDestination: NavDestination,
	navController: NavHostController
) {
	NavigationBarItem(
		label = { Text(text = screens.title) },
		icon = { Icon(imageVector = screens.icon, contentDescription = "Icons") },
		selected = currentDestination.hierarchy?.any {
			it.route == screens.route
		} == true,
		onClick = {
			navController.navigate(screens.route) {
				popUpTo(navController.graph.id)
				launchSingleTop = true
			}
		}
	)
}

@Composable
fun BottomNavGraph(
	navController: NavHostController,
	context: Context,
	modelo: UserViewModel = viewModel()
) {
	
	NavHost(navController = navController, startDestination = BottomNavScreen.Home.route) {
		composable(route = BottomNavScreen.Home.route) {
			//TODO
		}
		composable(route = BottomNavScreen.Citas.route) {
			
			ShowCitasScreen(modelo)
		}
		composable(route = BottomNavScreen.Cuenta.route) {
			//TODO
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


