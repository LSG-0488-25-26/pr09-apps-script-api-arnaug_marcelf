package com.example.script_api.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.script_api.model.Pandemic
import com.example.script_api.viewModel.PandemicsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPandemicScreen(
    navController: NavController,
    viewModel : PandemicsViewModel
) {
    var eventName by remember { mutableStateOf("") }
    var pathogenName by remember { mutableStateOf("") }
    var pathogenType by remember { mutableStateOf("") }
    var startYear by remember { mutableStateOf("") }
    var endYear by remember { mutableStateOf("") }
    var originRegion by remember { mutableStateOf("") }
    var geographicSpread by remember { mutableStateOf("") }
    var continentsAffected by remember { mutableStateOf("") }
    var estimatedCases by remember { mutableStateOf("") }
    var estimatedDeaths by remember { mutableStateOf("") }
    var caseFatalityRatePct by remember { mutableStateOf("") }
    var primaryTransmission by remember { mutableStateOf("") }
    var containmentMethod by remember { mutableStateOf("") }
    var mortalityScale by remember { mutableStateOf("") }
    var century by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Afegir nova pandèmia") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("⬅")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Nom de l'event") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pathogenName,
                onValueChange = { pathogenName = it },
                label = { Text("Nom del patogen") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pathogenType,
                onValueChange = { pathogenType = it },
                label = { Text("Tipus de patogen") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = startYear,
                onValueChange = { startYear = it },
                label = { Text("Any d'inici") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = endYear,
                onValueChange = { endYear = it },
                label = { Text("Any de final") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = originRegion,
                onValueChange = { originRegion = it },
                label = { Text("Regió d'origen") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = geographicSpread,
                onValueChange = { geographicSpread = it },
                label = { Text("Expansió geogràfica") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = continentsAffected,
                onValueChange = { continentsAffected = it },
                label = { Text("Continents afectats") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estimatedCases,
                onValueChange = { estimatedCases = it },
                label = { Text("Casos estimats") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estimatedDeaths,
                onValueChange = { estimatedDeaths = it },
                label = { Text("Morts estimades") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = caseFatalityRatePct,
                onValueChange = { caseFatalityRatePct = it },
                label = { Text("Taxa de mortalitat (%)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = primaryTransmission,
                onValueChange = { primaryTransmission = it },
                label = { Text("Transmissió principal") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = containmentMethod,
                onValueChange = { containmentMethod = it },
                label = { Text("Mètode de contenció") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = mortalityScale,
                onValueChange = { mortalityScale = it },
                label = { Text("Escala de mortalitat") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = century,
                onValueChange = { century = it },
                label = { Text("Segle") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val newPandemic = Pandemic(
                        eventName = eventName.ifBlank { "Desconegut" },
                        pathogenName = pathogenName.ifBlank { "Desconegut" },
                        pathogenType = pathogenType.ifBlank { "Desconegut" },
                        startYear = startYear.toIntOrNull() ?: 0,
                        endYear = endYear.toIntOrNull() ?: 0,
                        originRegion = originRegion.ifBlank { "Desconegut" },
                        geographicSpread = geographicSpread.ifBlank { "Desconegut" },
                        continentsAffected = continentsAffected.toIntOrNull() ?: 0,
                        estimatedCases = estimatedCases.toLongOrNull() ?: 0L,
                        estimatedDeaths = estimatedDeaths.toLongOrNull() ?: 0L,
                        caseFatalityRatePct = caseFatalityRatePct.toDoubleOrNull() ?: 0.0,
                        primaryTransmission = primaryTransmission.ifBlank { "Desconegut" },
                        containmentMethod = containmentMethod.ifBlank { "Desconegut" },
                        mortalityScale = mortalityScale.ifBlank { "Desconegut" },
                        century = century.toIntOrNull() ?: 0
                    )
                    scope.launch {
                        val success = viewModel.crearPandemia(accio = newPandemic)
                        if (success) {
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar nova pandèmia")
            }
        }
    }
}