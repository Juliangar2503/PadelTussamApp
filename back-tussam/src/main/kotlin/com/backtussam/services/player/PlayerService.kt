package com.backtussam.services.player

import com.backtussam.model.Player
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.UpdatePlayerParams

interface PlayerService {
    /*** OBTENER JUGADORES ***/
    suspend fun getPlayers(): List<Player>
    suspend fun getPlayersByLeague(leagueId: Int): List<Player>
    suspend fun getPlayersByName(name:String): List<Player>
    suspend fun getPlayerById(id: Int): Player?

    /*** REGISTRAR JUGADOR ***/
    suspend fun registerPlayer(params: CreatePlayerParams): Player?
    suspend fun addPlayerToLeague(playerId: Int, leagueId: Int): Boolean

    /*** ACTUALIZAR Y ELIMINAR JUGADOR ***/
    suspend fun updatePlayer(email: String, params: UpdatePlayerParams): Player?
    suspend fun deletePlayer(id: Int): Boolean

    /*** BUSCAR JUGADORES POR EMAIL Y PASSWORD ***/
    suspend fun findPlayerByEmail(email: String): Player?
    suspend fun findPasswordByEmail(email: String): String?
}