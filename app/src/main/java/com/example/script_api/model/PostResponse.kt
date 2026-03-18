package com.example.script_api.model

/**
 * Data class incomplet que servirà per a recollir la resposta
 * de la petició POST d'Apps Script.
 */
data class PostResponse(
    val success: Boolean,
    val message: String? = null
)