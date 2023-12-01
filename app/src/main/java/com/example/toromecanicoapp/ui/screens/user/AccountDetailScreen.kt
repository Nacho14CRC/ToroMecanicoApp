package com.example.toromecanicoapp.ui.screens.user

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.toromecanicoapp.R
import com.example.toromecanicoapp.ToroMecanicoBottomAppBar
import com.example.toromecanicoapp.ToroMecanicoTopAppBar
import com.example.toromecanicoapp.data.model.User
import com.example.toromecanicoapp.ui.navegation.Destinos
import com.example.toromecanicoapp.ui.screens.home.InicioDestino
import com.example.toromecanicoapp.viewmodels.UserViewModel
import com.google.firebase.storage.FirebaseStorage

object MiCuentaDestino : Destinos {
	override val ruta = "detalle_cuenta"
	override val tituloRecurso = R.string.identificacion_campo
	override val descripcionIcono = "Cuenta"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MiCuentaScreen(
	navegarALogin: () -> Unit,
	navegarAInicio: () -> Unit,
	navegarACitas: () -> Unit,
	navegarAMiCuenta: () -> Unit,
	currentDestination: NavDestination?,
	usuario: User?,
	modifier: Modifier = Modifier ,
	usuarioModel: UserViewModel = UserViewModel(),
	darkTheme: Boolean,
	onThemeUpdated: () -> Unit
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			ToroMecanicoTopAppBar(
				title = stringResource(InicioDestino.tituloRecurso),
				canNavigateBack = false,
				navegarALogin = navegarALogin,
				modelo = usuarioModel,
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
					TarjetaUsuario(usuario?.userId, usuario)
					ModoOscuro(darkTheme, onThemeUpdated)
				}
			}
		}
	}
}

@ExperimentalMaterial3Api
@Composable
fun TarjetaUsuario(
	idUsuario: String?,
	usuario: User?,
	modifier: Modifier = Modifier
) {
	val context = LocalContext.current
	var imageUrl by remember { mutableStateOf<String?>(null) }
	
	var galleryResultLauncher =
		rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
			uri?.let {
				idUsuario?.let { it1 ->
					cargarImagenAlStorage(it, it1, context) { downloadUri ->
						if (downloadUri != null) {
							imageUrl = downloadUri?.toString()
						}
					}
				}
			}
		}
	
	val imageReference = FirebaseStorage.getInstance().reference.child("images/" + idUsuario)
	
	imageReference.downloadUrl.addOnSuccessListener { uri ->
		imageUrl = uri.toString()
	}
	
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
				if (usuario != null) {
					Text(
						text = usuario.nombreCompleto,
						fontWeight = FontWeight.Bold,
						style = MaterialTheme.typography.displayLarge,
						modifier = Modifier
							.weight(0.40f)
					)
					
					if (imageUrl != null) {
						AsyncImage(
							model = ImageRequest.Builder(LocalContext.current)
								.data(imageUrl)
								.crossfade(true)
								.build(),
							contentDescription = "Imagen",
							contentScale = ContentScale.Crop,
							modifier = Modifier
								.clip(CircleShape)
								.size(72.dp)
						)
					} else {
						Image(
							painter = painterResource(id = R.drawable.ic_account_circle),
							contentDescription = "avatar",
							contentScale = ContentScale.Crop,
							modifier = Modifier
								.size(size = 72.dp)
								.clip(shape = CircleShape)
						)
					}
				}
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
				Button(
					onClick = {
						galleryResultLauncher.launch("image/*")
					},
					modifier = Modifier
						.height(28.dp)
						.width(76.dp)
				) {
					Text("Editar", fontSize = 10.sp)
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
					Text(text = "Identificación", fontWeight = FontWeight.Bold)
					if (usuario != null) {
						Text(text = usuario.identificacion)
					}
				}
				Column(
					modifier = Modifier
						.wrapContentHeight()
						.weight(1f)
				) {
					Text(
						text = "Tipo de usuario",
						modifier = Modifier.fillMaxWidth(),
						fontWeight = FontWeight.Bold
					)
					if (usuario != null) {
						Text(text = usuario.tipoUsuario, modifier = Modifier.fillMaxWidth())
					}
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
					if (usuario != null) {
						Text(text = usuario.telefono)
					}
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
					if (usuario != null) {
						Text(text = usuario.correo, modifier = Modifier.fillMaxWidth())
					}
				}
			}
		}
	}
}


fun cargarImagenAlStorage(uri: Uri, userId: String, context: Context, callback: (Uri?) -> Unit) {
	val storage = FirebaseStorage.getInstance()
	val storageRef = storage.reference
	val imageRef = storageRef.child("images/$userId")
	
	val uploadTask = imageRef.putFile(uri)
	
	uploadTask.continueWithTask { task ->
		if (!task.isSuccessful) {
			task.exception?.let {
				throw it
			}
		}
		imageRef.downloadUrl
	}.addOnCompleteListener { task ->
		if (task.isSuccessful) {
			val downloadUri = task.result
			Toast.makeText(context, "Imagen cargada", Toast.LENGTH_SHORT).show()
			callback(downloadUri) // Llamada a la función de retorno con downloadUri
		} else {
			Toast.makeText(context, "Error al cargar", Toast.LENGTH_SHORT).show()
			callback(null) // Llamada a la función de retorno con null en caso de error
		}
	}
}

@Composable
fun ModoOscuro(darkTheme: Boolean=false, onThemeUpdated: () -> Unit) {
	 val checkedState = remember { mutableStateOf(darkTheme) }
	
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
				text = stringResource(id = R.string.tema_noche_text)
			)
		}
		Switch(
			checked = checkedState.value,
			onCheckedChange = { isChecked ->
				checkedState.value = isChecked
				onThemeUpdated()
			}
		)
	}
}
