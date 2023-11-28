package com.example.toromecanicoapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.theme.ToroMecanicoAppTheme
import com.example.toromecanicoapp.viewmodels.UserViewModel

object InicioDestino : Destinos {
	override val ruta = "home"
	override val tituloRecurso = R.string.app_name
	override val descripcionIcono = "Home"
}

@Composable
fun InicioScreen(
	navegarALogin: () -> Unit,
	navegarAInicio: () -> Unit,
	navegarACitas: () -> Unit,
	navegarAMiCuenta: () -> Unit,
	currentDestination: NavDestination?,
	usuarioModelo: UserViewModel,
	modifier: Modifier = Modifier
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(InicioDestino.tituloRecurso),
				canNavigateBack = false,
				navegarALogin = navegarALogin,
				modelo = usuarioModelo,
				modifier = modifier
			)
		},
		bottomBar = {
			ToroMecanicoBottomAppBar(
				navegarAInicio,
				navegarACitas,
				navegarAMiCuenta,
				currentDestination
			)
		}
	) { innerPadding ->
		Column(modifier = Modifier.padding(innerPadding)) {
			HomeBody(
				modifier = modifier
					.padding(innerPadding)
					.fillMaxSize()
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun HomeBody(
	modifier: Modifier = Modifier
) {
	LazyColumn(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
	) {
		item {
			TopMecanicos()
		}
	}
}

@Composable
fun TopMecanicos(
	modifier: Modifier = Modifier
) {
	Text(
		text = "Top Mecánicos",
		style = MaterialTheme.typography.displaySmall,
		modifier = Modifier.padding(vertical = 4.dp, horizontal = 22.dp)
	)
	
	val cardData = listOf(
		"1",
		"2",
		"3",
		"4"
	)
	
	Row(
		modifier = Modifier
			.padding(horizontal = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(12.dp)
	) {
		cardData.forEach {
			Card(
				modifier = modifier
					.wrapContentHeight()
					.padding(all = 8.dp),
				shape = RoundedCornerShape(size = 8.dp),
				elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
			) {
				CardContent(it)
			}
		}
	}
}

@Composable
fun CardContent(label: String) {
	Column(
		modifier = Modifier
			.padding(start = 15.dp, top = 8.dp, end = 15.dp, bottom = 8.dp)
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_account_circle),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(size = 35.dp)
				.clip(shape = CircleShape)
				.align(Alignment.CenterHorizontally)
		)
		Text(
			text = label,
			modifier = Modifier
				.padding(vertical = 4.dp, horizontal = 8.dp)
				.align(Alignment.CenterHorizontally)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
	ToroMecanicoAppTheme {
		HomeBody(Modifier)
	}
}
