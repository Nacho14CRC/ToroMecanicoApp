package com.example.toromecanicoapp.navegation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.screens.createAccount.ShowCreateAccountScreen
import com.example.toromecanicoapp.screens.home.Home
import com.example.toromecanicoapp.screens.resetPassword.ShowForgotPassword
import com.example.toromecanicoapp.ui.screens.ShowLoginScreen
import com.example.toromecanicoapp.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun Navigation(context: Context, navController: NavHostController = rememberNavController()) {
	var modelo: UserViewModel = viewModel()
	val user: FirebaseUser? = modelo.getCurrentUser()
	val destination = getStartDestination(user)
	
	NavHost(
		navController = navController,
		startDestination = destination,
	) {
		composable(Screens.LoginScreen.name) {
			ShowLoginScreen(navController, modelo);
		}
		composable(Screens.HomeScreen.name) {
			Home(navController, modelo);
		}
		composable(Screens.CreateAccountScreen.name) {
			ShowCreateAccountScreen(navController, modelo);
		}
		composable(Screens.ResetPasswordScreen.name) {
			ShowForgotPassword(navController, modelo);
		}
	}
	
}

@Composable
private fun getStartDestination(user: FirebaseUser?): String {
	return if (user == null) {
		Screens.LoginScreen.name
	} else {
		Screens.HomeScreen.name
	}
}