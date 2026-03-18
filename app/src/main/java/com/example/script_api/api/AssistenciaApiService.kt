package com.example.script_api.api

import com.example.script_api.model.GetResponse
import com.example.script_api.model.Pandemic
import com.example.script_api.model.PostRequest
import com.example.script_api.model.PostResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interface que declara la signatura dels mètodes
 * referents als endpoints de la API de JS.
 */

interface AssistenciaApiService {
    /**
     * GET totes les dades de la pestanya "Full 1" del Google Sheets
     */
    @GET("exec")
    suspend fun getDadesPandemic(
        @Query("apiKey") apiKey: String,
        @Query("type") type: String = "all",
    ): GetResponse<List<Pandemic>>

    @GET("exec")
    suspend fun carregarDadesPerType(
        @Query("apiKey") apiKey: String,
        @Query("type") type: String = "all",
        @Query("pathogenType") pathogen: String,
    ): GetResponse<List<Pandemic>>

    @GET("exec")
    suspend fun carregarDadesPerCentury(
        @Query("apiKey") apiKey: String,
        @Query("type") type: String = "bycentury",
        @Query("century") century: String,
    ): GetResponse<List<Pandemic>>

    /**
     * POST per registrar entrada/sortida
     * @author RIS
     */
    @POST("exec")
    suspend fun inserirFila(
        @Body body: Pandemic
    ): PostResponse

    // POST per registrar entrada/sortida amb PostRequest
    @POST("exec")
    suspend fun enviarRegistre(
        @Body body: PostRequest
    ): PostResponse
}