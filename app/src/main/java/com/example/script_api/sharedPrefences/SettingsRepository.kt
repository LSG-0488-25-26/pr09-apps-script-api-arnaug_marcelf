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
}