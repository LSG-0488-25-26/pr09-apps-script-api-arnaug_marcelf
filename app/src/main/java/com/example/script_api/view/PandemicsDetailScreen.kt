package com.example.script_api.view
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

            Text(
                text = "🦠 ${pandemic.pathogenName}",
                style = MaterialTheme.typography.titleLarge
            )

            Text("Tipus: ${pandemic.pathogenType}")
            Text("Transmissió: ${pandemic.primaryTransmission}")

            Spacer(Modifier.height(10.dp))

            Text("📅 Període", style = MaterialTheme.typography.titleMedium)
            Text("Inici: ${pandemic.startYear}")
            Text("Final: ${pandemic.endYear}")
            Text("Segle: ${pandemic.century}")

            Spacer(Modifier.height(10.dp))

            Text("🌍 Geografia", style = MaterialTheme.typography.titleMedium)
            Text("Origen: ${pandemic.originRegion}")
            Text("Expansió: ${pandemic.geographicSpread}")
            Text("Continents afectats: ${pandemic.continentsAffected}")

            Spacer(Modifier.height(10.dp))

            Text("📊 Impacte", style = MaterialTheme.typography.titleMedium)
            Text("Casos estimats: ${pandemic.estimatedCases}")
            Text("Morts estimades: ${pandemic.estimatedDeaths}")
            Text("Taxa mortalitat: ${pandemic.caseFatalityRatePct}%")
            Text("Escala: ${pandemic.mortalityScale}")

            Spacer(Modifier.height(10.dp))

            Text("🛡 Contenció", style = MaterialTheme.typography.titleMedium)
            Text("Mètode: ${pandemic.containmentMethod}")
        }
    }
}