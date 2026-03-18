package com.example.script_api.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.script_api.view.AddNewPandemicScreen
import com.example.script_api.view.LoginScreen
import com.example.script_api.view.MeScreen
import com.example.script_api.view.PandemicDetailScreen
import com.example.script_api.view.PandemicScreen
import com.example.script_api.view.RegisterScreen
import com.example.script_api.viewModel.PandemicsViewModel
import com.example.script_api.viewModel.ScaffoldViewModel


@Composable
fun EntryPoint(navigationController: NavController, viewModel: PandemicsViewModel, scaffoldViewModel: ScaffoldViewModel, apiKey : String) {
    NavHost(
        navController = navigationController as NavHostController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Home.route) {
            PandemicScreen(navigationController, viewModel = viewModel, scaffoldViewModel = scaffoldViewModel, apiKey = apiKey)
        }

        composable(Routes.Login.route) {
            LoginScreen(viewModel = viewModel, navController = navigationController)
        }

        composable(Routes.Register.route) {
            RegisterScreen(viewModel = viewModel, navController = navigationController)
        }

        composable(Routes.AddPandemic.route) {
            AddNewPandemicScreen(
                navController = navigationController,
                viewModel = viewModel
            )
        }

        composable(Routes.Me.route) {
            MeScreen(viewModel = viewModel, navController = navigationController, scaffoldViewModel)
        }

        composable(
            Routes.PandemicsDetailScreen.route,
            arguments = listOf(
                navArgument("pandemicName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pandemicName = backStackEntry.arguments?.getString("pandemicName").orEmpty()
            PandemicDetailScreen(
                pandemicName = pandemicName,
                viewModel = viewModel,
                navController = navigationController
            )
        }
    }
}