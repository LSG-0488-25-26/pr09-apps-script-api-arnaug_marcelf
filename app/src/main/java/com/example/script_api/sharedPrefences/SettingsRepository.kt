package com.example.script_api.sharedPrefences
import android.content.Context
import android.content.SharedPreferences



class SettingsRepository {
    private val context: Context
    private val sharedPreferences: SharedPreferences

    constructor(nomFitxer: String, context: Context) {
        this.context = context
        this.sharedPreferences = context.getSharedPreferences(nomFitxer, Context.MODE_PRIVATE)
    }

    fun <T> saveSettingValue(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    fun <T> getSettingValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    fun guardarNom(valor_nom: String) {
        var key: String = "nom_usuari"
        sharedPreferences.edit().putString(key, valor_nom).apply()
    }

    fun guardarCorreu(valorCorreu: String) {
        var key: String = "correu_usuari"
        sharedPreferences.edit().putString(key, valorCorreu).apply()
    }

    fun guardarPassword(valorPassword: String) {
        val key = "pwd"
        sharedPreferences.edit().putString(key, valorPassword).apply()
    }

    fun obtenirNom(): String {
        var key: String = "nom_usuari"
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun obtenirCorreu(): String {
        var key: String = "correu_usuari"
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun obtenirPassword(): String {
        val key = "pwd"
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun clearUser() {
        sharedPreferences.edit().remove("nom_usuari")
            .remove("correu_usuari")
            .remove("pwd")
            .apply()
    }
}