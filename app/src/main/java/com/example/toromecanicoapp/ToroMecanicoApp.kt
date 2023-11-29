package com.example.toromecanicoapp

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.ui.navegation.ToroMecanicoNavHost
import com.example.toromecanicoapp.ui.screens.cita.CitasDestino
import com.example.toromecanicoapp.ui.screens.home.InicioDestino
import com.example.toromecanicoapp.ui.screens.user.MiCuentaDestino
import com.example.toromecanicoapp.viewmodels.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun ToroMecanicoApp(context: Context, navController: NavHostController = rememberNavController()) {
	ToroMecanicoNavHost(context, navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToroMecanicoTopAppBar(
	title: String,
	canNavigateBack: Boolean,
	navegarALogin: () -> Unit,
	modifier: Modifier,
	navegarAtras: () -> Unit = {},
	modelo: UserViewModel
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
				IconButton(onClick = navegarAtras) {
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
				navegarALogin()
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
				label = { Text(text = InicioDestino.descripcionIcono) },
				icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Icons") },
				selected = currentDestination.hierarchy?.any {
					it.route == InicioDestino.ruta
				} == true,
				onClick = {
					navigateToHome()
				}
			)
		}
		
		if (currentDestination != null) {
			NavigationBarItem(
				label = { Text(text = CitasDestino.descripcionIcono) },
				icon = {
					Icon(
						imageVector = Icons.Default.ViewTimeline,
						contentDescription = "Icons"
					)
				},
				selected = currentDestination.hierarchy?.any {
					it.route == CitasDestino.ruta
				} == true,
				onClick = {
					navigateToCitas()
				}
			)
		}
		
		
		if (currentDestination != null) {
			NavigationBarItem(
				label = { Text(text = MiCuentaDestino.descripcionIcono) },
				icon = {
					Icon(
						imageVector = Icons.Default.AccountCircle,
						contentDescription = "Icons"
					)
				},
				selected = currentDestination.hierarchy?.any {
					it.route == MiCuentaDestino.ruta
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


fun FormatearDate(dateMillis: Long): String {
	val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
	val date = Date(dateMillis)
	return dateFormat.format(date) //TODO
}