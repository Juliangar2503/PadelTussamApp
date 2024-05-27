package com.backtussam.services.match

import com.backtussam.model.Match
import com.backtussam.model.Player
import com.backtussam.utils.params.match.CreateMatchParams
import com.backtussam.utils.params.match.ResultMatchParams

interface MatchService {
    /****** OBTENER PARTIDOS ******/
    suspend fun getMatches(): List<Match?>
    suspend fun getMatchById(id: Int): Match?
    suspend fun getMatchesByLeague(leagueId: Int): List<Match?>
    suspend fun getMatchesByType(typoMatch:String): List<Match?>
    suspend fun getMatchesOpenByPlayer(playerId: Int): List<Match?>
    suspend fun getMatchesCloseByPlayer(playerId: Int): List<Match?>
    /***** CREAR, ACTUALIZAR Y ELIMINAR PARTIDOS ******/
    suspend fun createMatch(params:CreateMatchParams): Match?
    suspend fun updateMatch(id: Int, params:CreateMatchParams): Match?
    suspend fun deleteMatch(id: Int): Boolean
    /**** OPERACIONES CON JUGADORES EN PARTIDOS *****/
    suspend fun changeOpenState(idMatch: Int, open: Boolean): Match?
    suspend fun isPlayerInMatch(playerId: Int, matchId: Int): Boolean
    suspend fun getIdPlayersInMatch(matchId: Int): List<Int>
    suspend fun addPlayerInMatch(idPlayer: Int, idMatch: Int, position: Int) : Match?
    suspend fun removePlayerInMatch(idPlayer: Int, idMatch: Int, position: Int) : Match?
    suspend fun getPlaceOfPlayerInMatch(idMatch: Int, idPlayer: Int): Int
    /**** OPERACIONES CON RESULTADOS DE PARTIDOS *****/
    suspend fun loadResults(idMatch: Int, params:ResultMatchParams): Match?
    suspend fun calculateResults(idMatch: Int): Match?
    suspend fun confirmOneResults(idMatch: Int): Match?
    suspend fun confirmSecondResults(idMatch: Int): Match?
}