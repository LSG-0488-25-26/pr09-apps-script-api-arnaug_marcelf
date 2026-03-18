package com.example.script_api.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.script_api.viewModel.PandemicsViewModel
import com.example.script_api.nav.Routes
import com.example.script_api.viewModel.ScaffoldViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    viewModel: PandemicsViewModel,
    navController: NavHostController,
    scaffoldViewModel: ScaffoldViewModel
) {
    Scaffold(
        topBar = {},
        bottomBar = { MyBottomBar(scaffoldViewModel, navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues), // respecta padding del scaffold
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Perfil d'Usuari", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(20.dp))

                Text("Usuari: ${viewModel.username}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Correu: ${viewModel.correu}")

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate(Routes.Login.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        }
    )
}