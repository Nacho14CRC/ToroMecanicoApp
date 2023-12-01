package com.example.toromecanicoapp.ui.screens.cita

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.data.model.User
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.home.InicioDestino
import com.example.toromecanicoapp.viewmodels.UserViewModel

object CitasDestino : Destinos {
	override val ruta = "citas"
	override val tituloRecurso = R.string.app_name
	override val descripcionIcono = "Citas"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CitasScreen(
	navegarAIngresoCita: () -> Unit,
	navegarADetalleCita: (String?) -> Unit,
	navegarALogin: () -> Unit,
	navegarAInicio: () -> Unit,
	navegarACitas: () -> Unit,
	navegarAMiCuenta: () -> Unit,
	currentDestination: NavDestination?,
	usuario: User?,
	modifier: Modifier = Modifier,
	modelo: UserViewModel = viewModel(),
	citaModel: CitaViewModel = CitaViewModel()
) {
	val lstCitas by citaModel.GetCitas(usuario).collectAsState(emptyList())
	val scope = rememberCoroutineScope()
	
	Scaffold(topBar = {
		ToroMecanicoTopAppBar(
			title = stringResource(InicioDestino.tituloRecurso),
			canNavigateBack = false,
			navegarALogin = navegarALogin,
			modelo = modelo,
			modifier = modifier
		)
	}, bottomBar = {
		ToroMecanicoBottomAppBar(
			navegarAInicio, navegarACitas, navegarAMiCuenta, currentDestination
		)
	}, floatingActionButton = {
		if (usuario?.tipoUsuario != "Mecánico") {
		FloatingActionButton(
			onClick = navegarAIngresoCita
		) {
			Icon(imageVector = Icons.Default.Add, contentDescription = "Add Cita")
		}
		}
		
	}) { innerPadding ->
		
		CitasBody(
			lstCitas = lstCitas,
			onCitaClick = navegarADetalleCita,
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@Composable
private fun CitasBody(
	lstCitas: List<Cita>,
	onCitaClick: (String?) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
	) {
		if (lstCitas.isEmpty()) {
			Text(
				text = "No se encontraron citas",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.titleLarge
			)
		} else {
			ListaCitas(
				lstCitas = lstCitas,
				onCitaClick = { onCitaClick(it.id) },
				modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
			)
		}
	}
}

@Composable
private fun ListaCitas(
	lstCitas: List<Cita>, onCitaClick: (Cita) -> Unit, modifier: Modifier = Modifier
) {
	LazyColumn {
		items(lstCitas) { item ->
			CitaItem(cita = item,
				modifier = Modifier
					.padding(16.dp)
					.clickable { onCitaClick(item) })
		}
	}
}

@Composable
private fun CitaItem(
	cita: Cita, modifier: Modifier
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
					text = buildAnnotatedString {
						withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
							append("Cita: ")
						}
						withStyle(style = SpanStyle(fontSize = 16.sp)) {
							append(cita.fechaCita)
						}
					},
					style = MaterialTheme.typography.titleLarge
				)
			}
		}
	}
}

