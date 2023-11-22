package com.example.toromecanicoapp.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.screens.createAccount.ShowCreateAccountScreen
import com.example.toromecanicoapp.screens.home.Home
import com.example.toromecanicoapp.screens.resetPassword.ShowForgotPassword
import com.example.toromecanicoapp.ui.screens.ShowLoginScreen

@Composable
fun Navigation(){
	var navController  = rememberNavController();
	val destination  = getStartDestination ()
	
	NavHost(
		navController = navController ,
		startDestination = destination,
		 ){
		composable(Screens.LoginScreen.name){
			ShowLoginScreen(navController);
		}
		composable(Screens.HomeScreen.name){
			Home(navController);
		}
		composable(Screens.CreateAccountScreen.name){
			ShowCreateAccountScreen(navController);
		}
		composable(Screens.ResetPasswordScreen.name){
			ShowForgotPassword(navController);
		}
	}
	
}

@Composable
private fun getStartDestination (): String {
	return Screens.LoginScreen.name
	/*return if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
		Screens.LoginScreen.name
	} else {
		Screens.HomeScreen.name
	} AAC: Se debe rescomentar cuando ya se implemente el logout*/
}