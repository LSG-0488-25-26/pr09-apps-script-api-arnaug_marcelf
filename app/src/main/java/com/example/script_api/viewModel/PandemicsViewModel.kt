package com.example.script_api.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.script_api.BuildConfig
import com.example.script_api.api.RetrofitInstance
import com.example.script_api.model.Pandemic
import com.example.script_api.model.PostRequest
import com.example.script_api.sharedPrefences.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class PandemicsViewModel(private val repository: SettingsRepository): ViewModel() {
    // Variables per a peticions GET
    private val _pandemics = MutableStateFlow<List<Pandemic>>(emptyList())
    val pandemics: StateFlow<List<Pandemic>> = _pandemics.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Variables per a peticions POST
    private val _missatgeResposta = MutableStateFlow<Boolean?>(null)
    val missatgeResposta: StateFlow<Boolean?> = _missatgeResposta

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun toggleLoading() {
        _loading.value = !_loading.value
    }

    fun setLoading(status: Boolean) {
        _loading.value = status
    }

    fun carregarDades(apiKey: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val resposta = RetrofitInstance.api.getDadesPandemic(apiKey)
                if (resposta.status == "ok" && resposta.data != null) {
                    _pandemics.value = resposta.data
                    println("Dades rebudes: ${resposta.data}")
                } else {
                    _error.value = resposta.error ?: "Error desconegut"
                }
            } catch (e: Exception) {
                _error.value = e.message
                println("Error al executar carregarDades: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun carregarDadesPerType(apiKey: String, pathogenType: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resposta = RetrofitInstance.api.carregarDadesPerType(
                    apiKey = apiKey,
                    pathogen = pathogenType
                )
                if (resposta.status == "ok" && resposta.data != null) {
                    _pandemics.value = resposta.data.filter { it.pathogenType.equals(pathogenType, true) }
                } else {
                    _error.value = resposta.error ?: "Error desconegut"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun carregarDadesPerCentury(apiKey: String, century: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resposta = RetrofitInstance.api.carregarDadesPerCentury(
                    apiKey = apiKey,
                    century = century
                )
                if (resposta.status == "ok" && resposta.data != null) {
                    val centuryInt = century.toIntOrNull()
                    _pandemics.value = resposta.data.filter { it.century == centuryInt }
                } else {
                    _error.value = resposta.error ?: "Error desconegut"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun crearPandemia(
        accio: Pandemic,
        apiKey: String = BuildConfig.API_KEY
    ): Boolean {
        _loading.value = true
        return try {
            val email = repository.obtenirCorreu()

            val body = PostRequest(email, accio, apiKey)
            val resposta = RetrofitInstance.api.enviarRegistre(body)

            _missatgeResposta.value = resposta.success

            if (resposta.success) {
                val currentList = _pandemics.value.toMutableList()
                currentList.add(accio)
                _pandemics.value = currentList
            }

            resposta.success
        } catch (e: Exception) {
            e.printStackTrace()
            _missatgeResposta.value = false
            false
        } finally {
            _loading.value = false
        }
    }
    // Auth!!
    var username by mutableStateOf(repository.obtenirNom())
        private set

    var correu by mutableStateOf(repository.obtenirCorreu())
        private set

    var contrassenya by mutableStateOf(repository.obtenirPassword())
        private set

    fun registrarUsuari(usuari: String, pwd: String, email: String) : Boolean {

        if (usuari.isEmpty() || pwd.isEmpty() || email.isEmpty()) return false

        username = usuari
        correu = email
        contrassenya = pwd

        repository.guardarNom(username)
        repository.guardarPassword(contrassenya)
        repository.guardarCorreu(correu)

        return true
    }

    fun login(user: String, password: String, correuEnviat: String): Boolean {
        val storedUsuari = repository.obtenirNom()
        val storedPw = repository.obtenirPassword()
        val storedCorreu = repository.obtenirCorreu()

        if (username.isBlank() || password.isBlank() || correuEnviat.isBlank()) return false

        if (correuEnviat == storedCorreu && password == storedPw && user == storedUsuari) {
            username = storedUsuari
            contrassenya = storedPw
            correu = storedCorreu

            return true
        } else {
            return false
        }
    }

    fun logout() {
        repository.clearUser()
        username = ""
        correu = ""
        contrassenya = ""
    }
}