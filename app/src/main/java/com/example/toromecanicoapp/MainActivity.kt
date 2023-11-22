package com.example.toromecanicoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.toromecanicoapp.navegation.Navigation
import com.example.toromecanicoapp.ui.theme.ToroMecanicoAppTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		Thread.sleep(1200)
		installSplashScreen()
		
		setContent {
			ToroMecanicoAppTheme {
				Navigation()
			}
		}
	}
}