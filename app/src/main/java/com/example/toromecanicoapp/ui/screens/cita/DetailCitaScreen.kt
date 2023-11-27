package com.example.toromecanicoapp.ui.screens.cita

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ui.navegation.Destinations

object CitaDetailsDestination : Destinations {
	override val route = "cita_details"
	override val titleRes = R.string.crear_cuenta_text
	override val desIcono = ""
	const val citaIdArg = "citaId"
	val routeWithArgs = "$route/{$citaIdArg}"
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaDetailsScreen(
	citaId: String,
	navigateToEditItem: (Int) -> Unit,
	navigateBack: () -> Unit,
	citaModel: CitaViewModel = CitaViewModel()
) {
	/*val citaState by remember { mutableStateOf<Cita?>(null) }
	
	LaunchedEffect(citaId) {
		val result = withContext(Dispatchers.IO) {
			citaModel.getCita(citaId)
		}
		when (result) {
			is AuthRes.Success -> {
				val cita = result.data
				citaState = cita
			}
			is AuthRes.Error -> {
			}
		}
	}*/
	
	// The rest of your Composable code, using citaState as needed
}

/*
@Composable
private fun ItemDetailsBody(
	itemDetailsUiState: ItemDetailsUiState,
	onSellItem: () -> Unit,
	onDelete: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
	) {
		var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
		ItemDetails(
			item = itemDetailsUiState.itemDetails.toItem(), modifier = Modifier.fillMaxWidth()
		)
		Button(
			onClick = onSellItem,
			modifier = Modifier.fillMaxWidth(),
			shape = MaterialTheme.shapes.small,
			enabled = !itemDetailsUiState.outOfStock
		) {
			Text(stringResource(R.string.sell))
		}
		OutlinedButton(
			onClick = { deleteConfirmationRequired = true },
			shape = MaterialTheme.shapes.small,
			modifier = Modifier.fillMaxWidth()
		) {
			Text(stringResource(R.string.delete))
		}
		if (deleteConfirmationRequired) {
			DeleteConfirmationDialog(
				onDeleteConfirm = {
					deleteConfirmationRequired = false
					onDelete()
				},
				onDeleteCancel = { deleteConfirmationRequired = false },
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
			)
		}
	}
}


@Composable
fun ItemDetails(
	item: Item, modifier: Modifier = Modifier
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
			ItemDetailsRow(
				labelResID = R.string.item,
				itemDetail = item.name,
				modifier = Modifier.padding(
					horizontal = dimensionResource(
						id = R.dimen
							.padding_medium
					)
				)
			)
			ItemDetailsRow(
				labelResID = R.string.quantity_in_stock,
				itemDetail = item.quantity.toString(),
				modifier = Modifier.padding(
					horizontal = dimensionResource(
						id = R.dimen
							.padding_medium
					)
				)
			)
			ItemDetailsRow(
				labelResID = R.string.price,
				itemDetail = item.formatedPrice(),
				modifier = Modifier.padding(
					horizontal = dimensionResource(
						id = R.dimen
							.padding_medium
					)
				)
			)
		}
		
	}
}

@Composable
private fun ItemDetailsRow(
	@StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
) {
	Row(modifier = modifier) {
		Text(text = stringResource(labelResID))
		Spacer(modifier = Modifier.weight(1f))
		Text(text = itemDetail, fontWeight = FontWeight.Bold)
	}
}

@Composable
private fun DeleteConfirmationDialog(
	onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
	AlertDialog(onDismissRequest = { /* Do nothing */ },
		title = { Text(stringResource(R.string.attention)) },
		text = { Text(stringResource(R.string.delete_question)) },
		modifier = modifier,
		dismissButton = {
			TextButton(onClick = onDeleteCancel) {
				Text(text = stringResource(R.string.no))
			}
		},
		confirmButton = {
			TextButton(onClick = onDeleteConfirm) {
				Text(text = stringResource(R.string.yes))
			}
		})
}
*/