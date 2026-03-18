package com.example.script_api.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.script_api.sharedPrefences.SettingsRepository

class UsuariViewModelFactory(private val repository: SettingsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PandemicsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PandemicsViewModel(repository) as T
        }
        throw IllegalArgumentException("NO ES TROBA EL VM")
    }
}