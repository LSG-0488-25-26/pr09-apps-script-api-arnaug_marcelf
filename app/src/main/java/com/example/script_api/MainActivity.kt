package com.example.script_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.script_api.nav.EntryPoint
import com.example.script_api.sharedPrefences.SettingsRepository
import com.example.script_api.viewModel.PandemicsViewModel
import com.example.script_api.viewModel.ScaffoldViewModel
import com.example.script_api.viewModel.UsuariViewModelFactory

class MainActivity : ComponentActivity() {
    private val repository by lazy { SettingsRepository("UserSettings", context = this) }
    val pandemicsViewModel: PandemicsViewModel by viewModels {
        UsuariViewModelFactory(repository)
    }
    val scaffoldViewModel by viewModels<ScaffoldViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationController = rememberNavController()

            EntryPoint(
                navigationController = navigationController,
                viewModel = pandemicsViewModel,
                scaffoldViewModel = scaffoldViewModel,
                apiKey = BuildConfig.API_KEY
            )
        }
    }
}