package com.example.script_api.model

/**
 * Data class que falta acabar i que servirà per a
 * fer peticions de tipus POST a la API de JS.
 */
data class PostRequest(
    val email: String,
    val accio: Pandemic,
    val apiKey: String
)