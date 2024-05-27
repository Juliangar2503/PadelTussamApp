package com.backtussam.repositories.player

import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.LoginPlayerParams
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.match.CreateMatchParams
import com.backtussam.utils.params.match.ResultMatchParams
import com.backtussam.utils.params.player.UpdatePlayerParams


interface PlayerRepository {
    /*** OBTENER JUGADORES ***/
    suspend fun getPlayers(): BaseResponse<Any>
    suspend fun getPlayerByEmail(email: String): BaseResponse<Any>
    suspend fun getPlayerById(id: Int): BaseResponse<Any>
    suspend fun getPlayersByLeague(leagueId: Int): BaseResponse<Any>
    suspend fun getPlayersByName(name: String): BaseResponse<Any>
    suspend fun getPlayersByMatch(matchId: Int): BaseResponse<Any>
    suspend fun getGlobalQueryPlayer(orderField: String, filterField: String, filterValor: String): BaseResponse<Any>

    suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any>
    suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any>
    suspend fun addPlayerToLeague(playerId: Int, leagueId: Int): BaseResponse<Any>

    suspend fun updatePlayer(email:String, params: UpdatePlayerParams): BaseResponse<Any>
    suspend fun deletePlayer(id: Int): BaseResponse<Any>
    suspend fun addPlayerToMatch(playerId: Int, matchId: Int): BaseResponse<Any>
    suspend fun removePlayerFromMatch(playerId: Int, matchId: Int): BaseResponse<Any>

    suspend fun openMatch(playerId:Int, type:String): BaseResponse<Any>
    suspend fun uploadResultMatch(matchId: Int, params: ResultMatchParams): BaseResponse<Any>
    suspend fun confirmResultMatchTeamA(matchId: Int, playerId: Int): BaseResponse<Any>
    suspend fun confirmResultMatchTeamB(matchId: Int, playerId: Int): BaseResponse<Any>
    suspend fun choosePlacesToPlay(matchId: Int, playerId: Int): Int
    suspend fun earnPointsAfterCheckMatch(matchId: Int): BaseResponse<Any>
    suspend fun saveMatchInHistory(matchId: Int, playerId: Int): BaseResponse<Any>
}