package com.example.toromecanicoapp.ui.navegation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.toromecanicoapp.ui.screens.LoginDestination
import com.example.toromecanicoapp.ui.screens.ShowLoginScreen
import com.example.toromecanicoapp.ui.screens.cita.CitaDetailsDestination
import com.example.toromecanicoapp.ui.screens.cita.CitaEntryDestination
import com.example.toromecanicoapp.ui.screens.cita.CitaEntryScreen
import com.example.toromecanicoapp.ui.screens.cita.CitasDestination
import com.example.toromecanicoapp.ui.screens.cita.ShowCitasScreen
import com.example.toromecanicoapp.ui.screens.home.HomeDestination
import com.example.toromecanicoapp.ui.screens.home.ShowHomeScreen
import com.example.toromecanicoapp.ui.screens.user.CreateAccountDestination
import com.example.toromecanicoapp.ui.screens.user.CuentaDetailDestination
import com.example.toromecanicoapp.ui.screens.user.ResetPasswordDestination
import com.example.toromecanicoapp.ui.screens.user.ShowCreateAccountScreen
import com.example.toromecanicoapp.ui.screens.user.ShowCuentaScreen
import com.example.toromecanicoapp.ui.screens.user.ShowForgotPassword
import com.example.toromecanicoapp.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun ToroMecanicoNavHost(
	context: Context,
	navController: NavHostController,
	modifier: Modifier = Modifier,
) {
	var modelo: UserViewModel = viewModel()
	val user: FirebaseUser? = modelo.getCurrentUser()
	val destination = getStartDestination(user)
	
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination
	
	NavHost(
		navController = navController,
		startDestination = destination,
		modifier = modifier
	) {
		
		composable(route = LoginDestination.route) {
			ShowLoginScreen(
				navigateToResetPassword = {
					navController.navigate(ResetPasswordDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCreateAccount = {
					navController.navigate(CreateAccountDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToHome = {
					navController.navigate(HomeDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				modelo
			);
		}
		composable(route = CreateAccountDestination.route) {
			ShowCreateAccountScreen(
				navigateToLogin = { navController.navigate(LoginDestination.route) },
				modelo
			);
		}
		composable(route = ResetPasswordDestination.route) {
			ShowForgotPassword(
				navigateToLogin = { navController.navigate(LoginDestination.route) }, modelo
			);
		}
		composable(route = HomeDestination.route) {
			ShowHomeScreen(
				navigateToLogin = { navController.navigate(LoginDestination.route) },
				navigateToHome = {
					navController.navigate(HomeDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCitas = {
					navController.navigate(CitasDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCuenta = {
					navController.navigate(CuentaDetailDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				currentDestination,
				userModel = modelo
			)
		}
		composable(route = CitasDestination.route) {
			ShowCitasScreen(
				navigateToCitaEntry = { navController.navigate(CitaEntryDestination.route) },
				navigateToCitaUpdate = {
					navController.navigate("${CitaDetailsDestination.route}/${it}")
				},
				navigateToLogin = { navController.navigate(LoginDestination.route) },
				navigateToHome = {
					navController.navigate(HomeDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCitas = {
					navController.navigate(CitasDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCuenta = {
					navController.navigate(CuentaDetailDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				currentDestination,
				modelo = modelo
			)
		}
		composable(route = CitaEntryDestination.route) {
			CitaEntryScreen(
				navigateToLogin = { navController.navigate(LoginDestination.route) },
				navigateBack = { navController.popBackStack() },
				onNavigateUp = { navController.navigateUp() },
				userModel = modelo
			)
		}
		composable(route = CuentaDetailDestination.route) {
			ShowCuentaScreen(
				navigateToLogin = { navController.navigate(LoginDestination.route) },
				navigateToHome = {
					navController.navigate(HomeDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCitas = {
					navController.navigate(CitasDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				navigateToCuenta = {
					navController.navigate(CuentaDetailDestination.route) {
						popUpTo(navController.graph.id)
						launchSingleTop = true
					}
				},
				currentDestination,
				modelo = modelo
			)
		}
		
		
		/*
			composable(route = ItemEntryDestination.route) {
				ItemEntryScreen(
					navigateBack = { navController.popBackStack() },
					onNavigateUp = { navController.navigateUp() }
				)
			}
			composable(
				route = ItemDetailsDestination.routeWithArgs,
				arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
					type = NavType.IntType
				})
			) {
				ItemDetailsScreen(
					navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
					navigateBack = { navController.navigateUp() }
				)
			}
			composable(
				route = ItemEditDestination.routeWithArgs,
				arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
					type = NavType.IntType
				})
			) {
				ItemEditScreen(
					navigateBack = { navController.popBackStack() },
					onNavigateUp = { navController.navigateUp() }
				)
			}*/
	}
}

private fun getStartDestination(user: FirebaseUser?): String {
	return if (user == null) {
		LoginDestination.route
	} else {
		HomeDestination.route
	}
}