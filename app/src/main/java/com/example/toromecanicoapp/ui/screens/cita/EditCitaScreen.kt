package com.example.toromecanicoapp.ui.screens.cita

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.navegation.Destinations


object CitaEditDestination : Destinations {
	override val route = "cita_edit"
	override val titleRes = R.string.edit_cita_title
	override val desIcono = ""
	const val citaIdArg = "citaId"
	val routeWithArgs = "$route/{$citaIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
	navigateBack: () -> Unit,
	onNavigateUp: () -> Unit/*,
	modifier: Modifier = Modifier,
	viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory)*/
) {

}
