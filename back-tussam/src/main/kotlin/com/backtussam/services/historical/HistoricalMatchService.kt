package com.backtussam.services.historical

import com.backtussam.model.PlayerMatchesHistorical
import com.backtussam.utils.params.historical.CreateHistoricalMatch

interface HistoricalMatchService {
    /***** OBTENER PARTIDOS HISTORICOS ***/
    suspend fun getHistoricalMatch(id: Int): PlayerMatchesHistorical?
    suspend fun getHistoricalMatches(): List<PlayerMatchesHistorical>
    suspend fun getHistoricalMatchesByPlayer(id: Int): List<PlayerMatchesHistorical>

    /*** ACCIONES CON PARTIDOS HISTORICOS ***/
    suspend fun registerHistoricalMatch(params: CreateHistoricalMatch): PlayerMatchesHistorical?
    suspend fun updateHistoricalMatch(id: Int, params: CreateHistoricalMatch): PlayerMatchesHistorical?
    suspend fun deleteHistoricalMatch(id: Int): Boolean
}