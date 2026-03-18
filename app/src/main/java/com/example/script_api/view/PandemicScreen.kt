package com.example.script_api.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.script_api.viewModel.PandemicsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.script_api.viewModel.ScaffoldViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PandemicScreen(
    navController: NavHostController,
    viewModel: PandemicsViewModel = viewModel(),
    scaffoldViewModel : ScaffoldViewModel,
    apiKey: String
) {
    val pandemics by viewModel.pandemics.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Estat per Dropdown i TextField
    var selectedFilter by remember { mutableStateOf("All") }
    var filterText by remember { mutableStateOf("") }
    val filterOptions = listOf("All", "By Type", "By Century")

    if (pandemics.isEmpty()) {
        LaunchedEffect(Unit) {
            viewModel.carregarDades(apiKey)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Pandemics",
                            style = MaterialTheme.typography.titleLarge
                        )

                        var expanded by remember { mutableStateOf(false) }

                        Box {
                            TextButton(onClick = { expanded = true }) {
                                Text(selectedFilter)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                filterOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedFilter = option
                                            expanded = false
                                            filterText = ""
                                            when (selectedFilter) {
                                                "All" -> viewModel.carregarDades(apiKey)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },

        bottomBar = { MyBottomBar(scaffoldViewModel, navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            // Si seleccionem By Type o By Century, mostrem el TextField
            if (selectedFilter != "All") {
                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    label = {
                        Text(
                            if (selectedFilter == "By Type") "Escriu el Pathogen Type"
                            else "Escriu el Century"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Button(
                    onClick = {
                        when (selectedFilter) {
                            "By Type" -> viewModel.carregarDadesPerType(apiKey, filterText)
                            "By Century" -> viewModel.carregarDadesPerCentury(apiKey, filterText)
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text("Filtrar")
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    error != null -> Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    pandemics.isEmpty() -> Text(
                        "No hi ha dades disponibles",
                        modifier = Modifier.align(Alignment.Center)
                    )
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(pandemics) { pandemic ->
                            PandemicCard(pandemic, navController)
                        }
                    }
                }
            }
        }
    }
}