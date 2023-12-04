package com.example.toromecanicoapp.ui.screens.cita

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.data.model.User
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.components.MostrarSubmitButton
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object DetalleCitaDestino : Destinos {
	override val ruta = "cita_details"
	override val tituloRecurso = R.string.detail_cita_title
	override val descripcionIcono = ""
	const val idCitaArg = "citaId"
	val rutaArg = "$ruta/{$idCitaArg}"
}

@Composable
fun DetalleCitaScreen(
	id: String,
	navegarALogin: () -> Unit,
	navegarAnterior: () -> Unit,
	navegarAtras: () -> Unit,
	navegarAEditarCita: (String?) -> Unit,
	usuario: User?,
	usuarioModel: UserViewModel = viewModel(),
	modifier: Modifier = Modifier,
	citaModel: CitaViewModel = CitaViewModel()
) {
	val citaDetalle by citaModel.GetCitaByDocument(id).collectAsState(initial = null)
	val coroutineScope = rememberCoroutineScope()
	Scaffold(
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(DetalleCitaDestino.tituloRecurso),
				canNavigateBack = true,
				navegarALogin = navegarALogin,
				modelo = usuarioModel,
				modifier = modifier,
				navegarAtras = navegarAtras
			)
		},
		floatingActionButton = {
			if (usuario?.tipoUsuario != "Mecánico") {
				FloatingActionButton(
					onClick = { navegarAEditarCita(citaDetalle?.id) }
				) {
					Icon(imageVector = Icons.Default.Edit, contentDescription = null)
				}
			}
		}
	) { innerPadding ->
		DetalleCitaBody(
			citaDetalle = citaDetalle,
			usuario = usuario,
			onCancelar = {
				coroutineScope.launch {
					citaModel.CancelarCita(citaDetalle?.id)
					navegarAnterior()
				}
			},
			onAtender = {
				coroutineScope.launch {
					citaModel.EditarCita(
						citaDetalle?.id.toString(),
						citaDetalle?.userId.toString(),
						citaDetalle?.fechaCita.toString(),
						citaDetalle?.mecanico.toString(),
						citaDetalle?.observaciones.toString(),
						"ATE"
					)
					navegarAnterior()
				}
			},
			modifier = Modifier
				.padding(innerPadding)
				.verticalScroll(rememberScrollState())
		)
	}
}

@Composable
private fun DetalleCitaBody(
	citaDetalle: Cita?,
	usuario: User?,
	onCancelar: () -> Unit,
	onAtender: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
	) {
		var cancelarConfirmationRequired by rememberSaveable { mutableStateOf(false) }
		DetalleCitaCard(
			cita = citaDetalle, modifier = Modifier.fillMaxWidth()
		)
		if (usuario?.tipoUsuario != "Mecánico") {
			OutlinedButton(
				onClick = { cancelarConfirmationRequired = true },
				shape = MaterialTheme.shapes.extraLarge,
				colors = ButtonDefaults.outlinedButtonColors(
					containerColor = MaterialTheme.colorScheme.error,
					contentColor = MaterialTheme.colorScheme.onPrimary
				),
				modifier = Modifier.fillMaxWidth()
			) {
				Text(stringResource(R.string.dlg_btn_cancelar))
			}
			if (cancelarConfirmationRequired) {
				CanncelarCitaConfirmationDialog(
					onCancelarCita = {
						cancelarConfirmationRequired = false
						onCancelar()
					},
					onDismiss = { cancelarConfirmationRequired = false },
					modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
				)
			}
		} else {
			MostrarSubmitButton(
				sLabel = stringResource(R.string.atendido_text),
				habilitarBoton = true
			) {
				onAtender()
			}
		}
		
	}
}

@Composable
fun DetalleCitaCard(
	cita: Cita?, modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier, colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			contentColor = MaterialTheme.colorScheme.onPrimaryContainer
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(dimensionResource(id = R.dimen.padding_medium)),
			verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
		) {
			Row(modifier = modifier) {
				Text(text = "Fecha: ", fontWeight = FontWeight.Bold)
				if (cita != null) {
					Text(text = cita.fechaCita)
				}
			}
			Row(modifier = modifier) {
				Text(text = "Mecánico: ", fontWeight = FontWeight.Bold)
				if (cita != null) {
					Text(text = cita.mecanico)
				}
			}
			Row(modifier = modifier) {
				Text(text = "Observaciones: ", fontWeight = FontWeight.Bold)
				if (cita != null) {
					Text(text = cita.observaciones)
				}
			}
		}
		
	}
}

@Composable
private fun CanncelarCitaConfirmationDialog(
	onCancelarCita: () -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier
) {
	AlertDialog(onDismissRequest = { onDismiss },
		title = { Text(stringResource(R.string.dlg_atencion)) },
		text = { Text(stringResource(R.string.dlg_pregunta_cancelar)) },
		modifier = modifier,
		dismissButton = {
			Button(onClick = onDismiss) {
				Text(text = stringResource(R.string.No))
			}
		},
		confirmButton = {
			Button(onClick = onCancelarCita) {
				Text(text = stringResource(R.string.Si))
			}
		})
}