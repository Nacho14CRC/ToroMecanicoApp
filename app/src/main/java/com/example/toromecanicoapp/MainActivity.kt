package com.example.toromecanicoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toromecanicoapp.ui.screens.CrearCuentaScreen
import com.example.toromecanicoapp.ui.screens.LoginScreen
import com.example.toromecanicoapp.ui.screens.OlvidoContrasenaScreen
import com.example.toromecanicoapp.ui.theme.ToroMecanicoAppTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		//Implementación del splash
		Thread.sleep(1200)
		installSplashScreen()
		
		setContent {
			ToroMecanicoAppTheme {
				
				//Se crea el navController para la navegación entre las paginas del login,crear,olvidar
				var navControllerCurrent = rememberNavController();
				
				NavHost(
					navController = navControllerCurrent,
					startDestination = getString(R.string.rutaCrearCuenta/*rutaLogin*/),
					builder = {
						composable(
							route = getString(R.string.rutaLogin),
							content = { LoginScreen(navController = navControllerCurrent) })
						composable(
							route = getString(R.string.rutaCrearCuenta),
							content = { CrearCuentaScreen(navController = navControllerCurrent) })
						composable(
							route = getString(R.string.rutaOlviarContrasena),
							content = { OlvidoContrasenaScreen(navController = navControllerCurrent) })
					})
			}
		}
	}
}