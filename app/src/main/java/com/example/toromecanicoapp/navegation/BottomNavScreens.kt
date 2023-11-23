package com.example.toromecanicoapp.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
	object Home : BottomNavScreen(
		route = "home",
		title = "Home",
		icon = Icons.Default.Home
	)
	
	object Citas : BottomNavScreen(
		route = "citas",
		title = "Citas",
		icon = Icons.Default.ViewTimeline
	)
	
	object Cuenta : BottomNavScreen(
		route = "cuenta",
		title = "Cuenta",
		icon = Icons.Default.AccountCircle
	)
}