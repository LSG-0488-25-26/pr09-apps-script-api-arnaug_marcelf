package com.example.script_api.view

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.script_api.viewModel.PandemicsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PandemicDetailScreen(
    pandemicName: String,
    viewModel: PandemicsViewModel,
    navController: NavController
) {
    val pandemics by viewModel.pandemics.collectAsState()

    val decodedName = Uri.decode(pandemicName)
    val pandemic = pandemics.firstOrNull { it.pathogenName == decodedName } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pandemic.eventName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.BugReport, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = pandemic.pathogenName,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Text(
                text = "${pandemic.pathogenType} · ${pandemic.primaryTransmission}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            @Composable
            fun InfoCard(
                icon: ImageVector,
                title: String,
                content: @Composable ColumnScope.() -> Unit
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        content()
                    }
                }
            }

            // Periode
            InfoCard(Icons.Filled.DateRange, "Període") {
                Text("Inici: ${pandemic.startYear}")
                Text("Final: ${pandemic.endYear}")
                Text("Segle: ${pandemic.century}")
            }

            // Geografia
            InfoCard(Icons.Filled.Public, "Geografia") {
                Text("Origen: ${pandemic.originRegion}")
                Text("Expansió: ${pandemic.geographicSpread}")
                Text("Continents: ${pandemic.continentsAffected}")
            }

            // Impacte
            InfoCard(Icons.Filled.BarChart, "Impacte") {
                Text("Casos: ${pandemic.estimatedCases}")
                Text("Morts: ${pandemic.estimatedDeaths}")
                Text("Mortalitat: ${pandemic.caseFatalityRatePct}%")
                Text("Escala: ${pandemic.mortalityScale}")
            }

            // Cotnencio
            InfoCard(Icons.Filled.Security, "Contenció") {
                Text("Mètode: ${pandemic.containmentMethod}")
            }
        }
    }
}