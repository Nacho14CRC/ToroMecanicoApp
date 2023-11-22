package com.example.toromecanicoapp.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.screens.crear_cuenta.MostrarCrearCuentaScreen
import com.example.toromecanicoapp.screens.home.Home
import com.example.toromecanicoapp.screens.olvido_contrasena.MostrarOlvidoContrasena
import com.example.toromecanicoapp.ui.screens.MostrarLoginScreen

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
		composable(toroMecanicoScreens.CrearCuentaScreen.name){
			MostrarCrearCuentaScreen(navControllerCurrent);
		}
		composable(toroMecanicoScreens.RestablecerContrasenaScreen.name){
			MostrarOlvidoContrasena(navControllerCurrent);
		}
	}
	
}
