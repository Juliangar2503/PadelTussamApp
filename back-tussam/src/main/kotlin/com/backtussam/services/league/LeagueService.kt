package com.backtussam.services.league

import com.backtussam.model.League
import com.backtussam.utils.params.league.CreateLeagueParams

interface LeagueService {
    /*** OBTENER LIGAS ***/
    suspend fun getLeague(name:String): League?
    suspend fun getLeagueById(id:Int): League?
    suspend fun getLeagues(): List<League>

    /*** ACCIONES CON LIGAS ***/
    suspend fun updateLeague(name:String, params:CreateLeagueParams): League?
    suspend fun registerLeagueByName(name:String): League?
    suspend fun registerLeague(params:CreateLeagueParams): League?
    suspend fun deleteLeague(name:String): Boolean
}