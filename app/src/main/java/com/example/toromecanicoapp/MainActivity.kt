package com.example.toromecanicoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.toromecanicoapp.ui.theme.ToroMecanicoAppTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		Thread.sleep(1200)
		installSplashScreen()
		
		setContent {
			var darkTheme by remember { mutableStateOf(false) }
			ToroMecanicoAppTheme(darkTheme=darkTheme) {
				ToroMecanicoApp(this,darkTheme, onThemeUpdated={darkTheme=!darkTheme})
			}
		}
	}
}