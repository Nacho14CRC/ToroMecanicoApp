package com.example.toromecanicoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.toromecanicoapp.ui.theme.ToroMecanicoAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToroMecanicoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Inicio()
                }
            }
        }
    }
}
@Composable
fun Inicio() {
    var esCargando by remember { mutableStateOf(true) }

    LaunchedEffect(esCargando) {
        delay(4000)
        esCargando = false
    }

    if (esCargando) {
        MostrarCargando()
    } else {
        MostrarPrincipal()
    }
}

@Composable
fun MostrarCargando() {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.principal))
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        val icon = painterResource(id = R.drawable.ic_cargando)
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.ic_cargando_label)
        )
    }
}

@Composable
fun MostrarPrincipal() {
      //TODO ACA se deberia de validar la sesión y luego redireccionar al login o al main
        Text(text = "¡Bienvenido a mi aplicación!")
}