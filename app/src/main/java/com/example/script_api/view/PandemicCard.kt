package com.example.script_api.view

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.script_api.model.Pandemic
import com.example.script_api.nav.Routes
import com.example.script_api.viewModel.PandemicsViewModel

/**
 * Funció composable que crea una composable Card per a cada objecte Arc rebut per paràmetre.
 * @author RIS
 * @param pandemic de tipus Arc
 */
@Composable
fun PandemicCard(pandemic: Pandemic, navController: NavController) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable() {
                navController.navigate(
                    Routes.PandemicsDetailScreen.createRoute(pandemic.pathogenName)
                )
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = pandemic.eventName,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("🎬 Inici episodi: ${pandemic.startYear}")
            Text("📺 Total episodis: ${pandemic.endYear}")
            Text("⏱ Total minuts: ${pandemic.caseFatalityRatePct}")
            Text("📖 Total pàgines: ${pandemic.mortalityScale}")
        }
    }
}