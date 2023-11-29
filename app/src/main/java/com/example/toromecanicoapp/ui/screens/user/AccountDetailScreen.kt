package com.example.toromecanicoapp.ui.screens.user

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.data.model.User
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.cita.CitaViewModel
import com.example.toromecanicoapp.ui.screens.home.InicioDestino
import com.example.toromecanicoapp.viewmodels.UserViewModel

object MiCuentaDestino : Destinos {
	override val ruta = "detalle_cuenta"
	override val tituloRecurso = R.string.identificacion_usuario
	override val descripcionIcono = "Cuenta"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MiCuentaScreen(
	id: String,
	navegarALogin: () -> Unit,
	navegarAInicio: () -> Unit,
	navegarACitas: () -> Unit,
	navegarAMiCuenta: () -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier = Modifier,
	modelo: UserViewModel,
	usuarioModel: UserViewModel = UserViewModel()
) {
	val idUsuario = usuarioModel.getCurrentUser()?.uid
	val usuario by usuarioModel.GetByDocument(idUsuario.toString()).collectAsState(initial = null)
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(InicioDestino.tituloRecurso),
				canNavigateBack = false,
				navegarALogin = navegarALogin,
				modelo = modelo,
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
		Column(
			modifier = Modifier.padding(innerPadding)
		) {
			LazyColumn(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
			) {
				item {
					TarjetaUsuario(usuario)
				}
				item {
					ModoOscuro()
				}
			}
		}
	}
}

@ExperimentalMaterial3Api
@Composable
fun TarjetaUsuario(
	usuario: User?,
	modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(all = 16.dp),
		shape = RoundedCornerShape(size = 16.dp),
		elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
	) {
		Column(
			modifier = Modifier.padding(all = 16.dp)
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				if(usuario != null) {
					Text(
						text = usuario.nombreCompleto,
						fontWeight = FontWeight.Bold,
						style = MaterialTheme.typography.displayLarge
					)
				}
				Image(
					painter = painterResource(id = R.drawable.ic_account_circle),
					contentDescription = "avatar",
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.size(size = 72.dp)
						.clip(shape = CircleShape)
				)
			}
			
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
					.padding(vertical = 8.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(text = "")
				Card(
					modifier = Modifier.wrapContentSize(),
					shape = RoundedCornerShape(size = 16.dp),
					
					elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
				) {
					Text(
						text = "Editar",
						modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
					)
				}
			}
			
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
					.padding(top = 16.dp)
			) {
				Column(
					modifier = Modifier
						.wrapContentHeight()
						.weight(1f)
				) {
					Text(text = "Id", fontWeight = FontWeight.Bold)
					Text(text = "F55455656")
				}
				Column(
					modifier = Modifier
						.wrapContentHeight()
						.weight(1f)
				) {
					Text(
						text = "Tipo",
						modifier = Modifier.fillMaxWidth(),
						fontWeight = FontWeight.Bold
					)
					Text(text = "Cliente", modifier = Modifier.fillMaxWidth())
				}
			}
			
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
					.padding(top = 16.dp)
			) {
				Column(
					modifier = Modifier
						.wrapContentHeight()
						.weight(1f)
				) {
					Text(text = "Telefono", fontWeight = FontWeight.Bold)
					Text(text = "3262652")
				}
				Column(
					modifier = Modifier
						.wrapContentHeight()
						.weight(1f)
				) {
					Text(
						text = "Correo",
						modifier = Modifier.fillMaxWidth(),
						fontWeight = FontWeight.Bold
					)
					Text(text = "FFFF", modifier = Modifier.fillMaxWidth())
				}
			}
		}
	}
}

@Composable
fun ModoOscuro() {
	val isDarkTheme =
		LocalContext.current.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
	val checkedState = remember { mutableStateOf(isDarkTheme) }
	
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.clickable(onClick = { checkedState.value = !checkedState.value })
			.padding(horizontal = 16.dp, vertical = 10.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Row(modifier = Modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically) {
			Icon(
				imageVector = Icons.Default.ModeNight,
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onBackground,
				modifier = Modifier.padding(end = 16.dp)
			)
			Text(
				modifier = Modifier.wrapContentSize(),
				text = stringResource(id = R.string.tema_noche)
			)
		}
		Switch(
			checked = checkedState.value,
			onCheckedChange = { isChecked ->
				checkedState.value = isChecked
				if (isChecked) {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				} else {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				}
			}
		)
	}
}