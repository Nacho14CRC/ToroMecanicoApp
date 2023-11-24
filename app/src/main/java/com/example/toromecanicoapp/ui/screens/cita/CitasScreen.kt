package com.example.toromecanicoapp.ui.screens.cita

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.data.db.FirestoreManager
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.ui.navegation.Destinations
import com.example.toromecanicoapp.ui.screens.home.HomeDestination
import com.example.toromecanicoapp.viewmodels.UserViewModel

object CitasDestination : Destinations {
	override val route = "citas"
	override val titleRes = R.string.identificacion_usuario
	override val desIcono = "Citas"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowCitasScreen(
	navigateToCitaEntry: () -> Unit,
	navigateToCitaUpdate: (Int) -> Unit,
	navigateToLogin: () -> Unit,
	navigateToHome: () -> Unit,
	navigateToCitas: () -> Unit,
	navigateToCuenta: () -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier = Modifier,
	modelo: UserViewModel
) {
	val db = FirestoreManager(modelo)
	val citas by db.getCitasFlow().collectAsState(emptyList())
	val scope = rememberCoroutineScope()
	
	Scaffold(
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(HomeDestination.titleRes),
				canNavigateBack = false,
				navigateToLogin = navigateToLogin,
				modelo = modelo,
				modifier = modifier
			)
		},
		bottomBar = {
			ToroMecanicoBottomAppBar(
				navigateToHome,
				navigateToCitas,
				navigateToCuenta,
				currentDestination
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = navigateToCitaEntry
			) {
				Icon(imageVector = Icons.Default.Add, contentDescription = "Add Cita")
			}
		}
	) { innerPadding ->
		
		CitasBody(
			itemList = citas,
			onItemClick = navigateToCitaUpdate,
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@Composable
private fun CitasBody(
	itemList: List<Cita>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		if (itemList.isEmpty()) {
			Text(
				text = "No se encontraron citas",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.titleLarge
			)
		} else {
			CitasList(
				itemList = itemList,
				onItemClick = { onItemClick( 1/*TODO it.id*/) },
				modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
			)
		}
	}
}

@Composable
private fun CitasList(
	itemList: List<Cita>, onItemClick: (Cita) -> Unit, modifier: Modifier = Modifier
) {
	LazyColumn {
		items(itemList) { item ->
			CitaItem(item = item,
				modifier = Modifier
					.padding(dimensionResource(id = R.dimen.padding_small))
					.clickable { onItemClick(item) })
		}
	}
}

@Composable
private fun CitaItem(
	item: Cita, modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
	) {
		Column(
			modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
			verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
		) {
			Row(
				modifier = Modifier.fillMaxWidth()
			) {
				Text(
					text = item.observaciones,
					style = MaterialTheme.typography.titleLarge,
				)
				Spacer(Modifier.weight(1f))
				Text(
					text = item.observaciones,
					style = MaterialTheme.typography.titleLarge
				)
			}
			Text(
				text = item.observaciones,
				style = MaterialTheme.typography.titleLarge
			)
		}
	}
}

