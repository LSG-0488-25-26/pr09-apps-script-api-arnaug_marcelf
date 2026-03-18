package com.example.script_api.model

data class Pandemic(
    val eventName: String,
    val pathogenName: String,
    val pathogenType: String,
    val startYear: Int,
    val endYear: Int,
    val originRegion: String,
    val geographicSpread: String,
    val continentsAffected: Int,
    val estimatedCases: Long,
    val estimatedDeaths: Long,
    val caseFatalityRatePct: Double,
    val primaryTransmission: String,
    val containmentMethod: String,
    val mortalityScale: String,
    val century: Int
)