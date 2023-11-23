package com.example.toromecanicoapp.screens.citas

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toromecanicoapp.data.db.FirestoreManager
import com.example.toromecanicoapp.data.model.Cita
import com.example.toromecanicoapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowCitasScreen(modelo: UserViewModel) {
	val db = FirestoreManager(modelo)
	val citas by db.getCitasFlow().collectAsState(emptyList())
	val scope = rememberCoroutineScope()
	
	
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					scope.launch {	  //TODO
					db.addCita("Pruebas1")
					}
				},
			) {
				Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
			}
		}
	) {
		if (!citas.isNullOrEmpty()) {
			LazyVerticalStaggeredGrid(
				columns = StaggeredGridCells.Fixed(2),
				contentPadding = PaddingValues(4.dp)
			) {
				citas.forEach {
					item {
						CitaItem(cita = it, firestore = db)
					}
				}
			}
		} else {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(16.dp),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Icon(
					imageVector = Icons.Default.List,
					contentDescription = null,
					modifier = Modifier.size(100.dp)
				)
				Spacer(modifier = Modifier.height(16.dp))
				Text(
					text = "No se encontraron \nCitas",
					fontSize = 18.sp, fontWeight = FontWeight.Thin,
					textAlign = TextAlign.Center
				)
			}
		}
	}
}

@Composable
fun CitaItem(cita: Cita, firestore: FirestoreManager) {
	
	Card(
		modifier = Modifier.padding(6.dp),
	) {
		Column(
			modifier = Modifier
				.padding(12.dp)
				.fillMaxWidth()
		) {
			Text(text = cita.observaciones,
				fontWeight = FontWeight.Bold,
				fontSize = 20.sp,)
			Spacer(modifier = Modifier.height(8.dp))
		}
	}
}

