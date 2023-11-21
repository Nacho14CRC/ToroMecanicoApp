package com.example.toromecanicoapp.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.screens.home.Home
import com.example.toromecanicoapp.screens.login.MostrarLoginScreen

@Composable
fun toroMecanicoNavegation(){
	var navControllerCurrent = rememberNavController();
	
	NavHost(
		navController = navControllerCurrent,
		startDestination = toroMecanicoScreens.LoginScreen.name,
		 ){
		composable(toroMecanicoScreens.LoginScreen.name){
			MostrarLoginScreen(navControllerCurrent);
		}
		composable(toroMecanicoScreens.HomeScreen.name){
			Home(navControllerCurrent);
		}
	}
	
}
