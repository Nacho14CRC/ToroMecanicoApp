package com.example.toromecanicoapp.ui.navegation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.toromecanicoapp.ui.screens.LoginDestino
import com.example.toromecanicoapp.ui.screens.LoginScreen
import com.example.toromecanicoapp.ui.screens.cita.AgregarCitaDestino
import com.example.toromecanicoapp.ui.screens.cita.AgregarCitaScreen
import com.example.toromecanicoapp.ui.screens.cita.CitasDestino
import com.example.toromecanicoapp.ui.screens.cita.CitasScreen
import com.example.toromecanicoapp.ui.screens.cita.DetalleCitaDestino
import com.example.toromecanicoapp.ui.screens.cita.DetalleCitaScreen
import com.example.toromecanicoapp.ui.screens.cita.EditarCitaDestino
import com.example.toromecanicoapp.ui.screens.cita.EditarCitaScreen
import com.example.toromecanicoapp.ui.screens.home.InicioDestino
import com.example.toromecanicoapp.ui.screens.home.InicioScreen
import com.example.toromecanicoapp.ui.screens.user.CrearCuentaDestino
import com.example.toromecanicoapp.ui.screens.user.CrearCuentaScreen
import com.example.toromecanicoapp.ui.screens.user.MiCuentaDestino
import com.example.toromecanicoapp.ui.screens.user.MiCuentaScreen
import com.example.toromecanicoapp.ui.screens.user.RecuperarContrasenaDestino
import com.example.toromecanicoapp.ui.screens.user.RecuperarContrasenaScreen
import com.example.toromecanicoapp.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun ToroMecanicoNavHost(
	context: Context,
	navController: NavHostController,
	modifier: Modifier = Modifier,
) {
	var usuarioModel: UserViewModel = viewModel()
	val idUsuario: FirebaseUser? = usuarioModel.getCurrentUser()
	val inicio = ObtenerScreenInicio(idUsuario)
	
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination
	
	NavHost(
		navController = navController,
		startDestination = inicio,
		modifier = modifier
	) {
		
		composable(route = LoginDestino.ruta) {
			LoginScreen(
				navegarARecuperarContrasena = {
					navController.navigate(RecuperarContrasenaDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarACrearCuenta = {
					navController.navigate(CrearCuentaDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarAInicio = {
					navController.navigate(InicioDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				usuarioModel
			);
		}
		composable(route = CrearCuentaDestino.ruta) {
			CrearCuentaScreen(
				navegarALogin = { navController.navigate(LoginDestino.ruta) },
				usuarioModel
			);
		}
		composable(route = RecuperarContrasenaDestino.ruta) {
			RecuperarContrasenaScreen(
				navegarALogin = { navController.navigate(LoginDestino.ruta) }, usuarioModel
			);
		}
		composable(route = InicioDestino.ruta) {
			InicioScreen(
				navegarALogin = { navController.navigate(LoginDestino.ruta) },
				navegarAInicio = {
					navController.navigate(InicioDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarACitas = {
					navController.navigate(CitasDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarAMiCuenta = {
					navController.navigate(MiCuentaDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				currentDestination,
				usuarioModelo = usuarioModel
			)
		}
		composable(route = CitasDestino.ruta) {
			CitasScreen(
				navegarAIngresoCita = { navController.navigate(AgregarCitaDestino.ruta) },
				navegarADetalleCita = {
					navController.navigate("${DetalleCitaDestino.ruta}/${it}")
				},
				navegarALogin = { navController.navigate(LoginDestino.ruta) },
				navegarAInicio = {
					navController.navigate(InicioDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarACitas = {
					navController.navigate(CitasDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarAMiCuenta = {
					navController.navigate(MiCuentaDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				currentDestination,
				modelo = usuarioModel
			)
		}
		composable(route = AgregarCitaDestino.ruta) {
			AgregarCitaScreen(
				navegarALogin = { navController.navigate(LoginDestino.ruta) },
				navegarAnterior = { navController.popBackStack() },
				navegarAtras = { navController.navigateUp() },
				userModel = usuarioModel
			)
		}
		composable(
			route = DetalleCitaDestino.rutaArg,
			arguments = listOf(navArgument(DetalleCitaDestino.idCitaArg) {
				type = NavType.StringType
			})
		) { backStackEntry ->
			val id = backStackEntry.arguments?.getString(DetalleCitaDestino.idCitaArg)
			id?.let {
				DetalleCitaScreen(
					id = it,
					navegarALogin = { navController.navigate(LoginDestino.ruta) },
					navegarAnterior = { navController.popBackStack() },
					navegarAtras = { navController.navigateUp() },
					navegarAEditarCita = { navController.navigate("${EditarCitaDestino.ruta}/$it") },
					usuarioModel = usuarioModel
				)
			}
		}
		composable(
			route = EditarCitaDestino.rutaArgumentos,
			arguments = listOf(navArgument(EditarCitaDestino.citaIdArg) {
				type = NavType.StringType
			})
		) { backStackEntry ->
			val id = backStackEntry.arguments?.getString(DetalleCitaDestino.idCitaArg)
			id?.let {
				EditarCitaScreen(
					id = it,
					navegarALogin = { navController.navigate(LoginDestino.ruta) },
					navegarAnterior = { navController.popBackStack() },
					navegarAtras = { navController.navigateUp() },
					usuarioModel = usuarioModel
				)
			}
		}
		
		composable(route = MiCuentaDestino.ruta) {
			MiCuentaScreen(
				idUsuario.toString(),
				navegarALogin = { navController.navigate(LoginDestino.ruta) },
				navegarAInicio = {
					navController.navigate(InicioDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarACitas = {
					navController.navigate(CitasDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navegarAMiCuenta = {
					navController.navigate(MiCuentaDestino.ruta) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				currentDestination,
				modelo = usuarioModel
			)
		}
	}
}

private fun ObtenerScreenInicio(user: FirebaseUser?): String {
	return if (user == null) {
		LoginDestino.ruta
	} else {
		InicioDestino.ruta
	}
}