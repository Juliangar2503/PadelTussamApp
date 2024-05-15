package com.backtussam.repositories.player

import com.backtussam.security.JWTConfig
import com.backtussam.security.hash
import com.backtussam.services.league.LeagueService
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.LoginPlayerParams
import com.backtussam.services.player.PlayerService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.player.UpdatePlayerParams

class PlayerRepositoryImpl(
    private val playerService: PlayerService,
    private val leagueService: LeagueService
) : PlayerRepository {
    // Comprobar si el email ya está registrado
    override suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any> {
        return if (isEmailExist(params.email)){
            BaseResponse.ErrorResponse(message = "Email already registered")
        }else{
            // Registrar jugador
            val player = playerService.registerPlayer(params)
            if (player != null){
                // Crear token
                val token = JWTConfig.instance.createToken(player.id.toString())
                player.authToken = token

                // Responder con el jugador y el token creado
                BaseResponse.SuccessResponse(data = player)
            }else{
                BaseResponse.ErrorResponse(message = "Error creating player")
            }
        }
    }

    override suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any> {
        // Comprobar si el jugador existe
        val player = playerService.findPlayerByEmail(params.email)
        // Comprobar si la contraseña es correcta
        val encryptedPassword = playerService.findPasswordByEmail(params.email)
        // Comprobar si el jugador existe y la contraseña es correcta
        return if (player != null){
            if (encryptedPassword == hash(params.password)){
                // Crear token
                val token = JWTConfig.instance.createToken(player.id.toString())
                player.authToken = token
                // Jugador encontrado y contraseña correcta
                BaseResponse.SuccessResponse(data = player)
            }else{
                // Contraseña incorrecta
                BaseResponse.ErrorResponse(message = "Invalid password")
            }
        }else{
            // Jugador no encontrado
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun addPlayerToLeague(playerId: Int, leagueId: Int): BaseResponse<Any> {
        // Comprobar si el jugador existe
        val player = playerService.getPlayerById(playerId)
        return if (player != null){
            // Comprobar si la liga está registrada
            val league = leagueService.getLeagueById(leagueId)
            if (league != null){
                // Añadir jugador a la liga
                val added = playerService.addPlayerToLeague(playerId,leagueId)
                if (added){
                    BaseResponse.SuccessResponse(data = "Player added to league")
                }else{
                    BaseResponse.ErrorResponse(message = "Error adding player to league")
                }
            }else{
                BaseResponse.ErrorResponse(message = "League not found")
            }
        }else{
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun getPlayerByEmail(email: String): BaseResponse<Any> {
        val player = playerService.findPlayerByEmail(email)
        return if (player != null){
            BaseResponse.SuccessResponse(data = player)
        }else{
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun getPlayerById(id: Int): BaseResponse<Any> {
        val player = playerService.getPlayerById(id)
        return if (player != null){
            BaseResponse.SuccessResponse(data = player)
        }else{
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun getPlayers(): BaseResponse<Any> {
        //Comprobar si hay jugadores registrados
        val players = playerService.getPlayers()
        return if (players.isNotEmpty()){
            BaseResponse.SuccessResponse(data = players)
        }else{
            BaseResponse.ErrorResponse(message = "No players found")
        }
    }

    override suspend fun getPlayersByLeague(leagueId: Int): BaseResponse<Any> {
        //Comprobar si la liga está registrada
        val league = leagueService.getLeagueById(leagueId)
        return if (league != null){
            //Comprobar si hay jugadores registrados en la liga
            val players = playerService.getPlayersByLeague(leagueId)
            if (players.isNotEmpty()){
                BaseResponse.SuccessResponse(data = players)
            }else{
                BaseResponse.ErrorResponse(message = "No players found in league")
            }
        }else{
            BaseResponse.ErrorResponse(message = "League not found")
        }
    }

    override suspend fun getPlayersByName(name: String): BaseResponse<Any> {
        //Comprobar si hay jugadores registrados con ese nombre
        val players = playerService.getPlayersByName(name)
        return if (players.isNotEmpty()){
            BaseResponse.SuccessResponse(data = players)
        }else{
            BaseResponse.ErrorResponse(message = "No players found with that name")
        }
    }

    override suspend fun updatePlayer(email: String, params: UpdatePlayerParams): BaseResponse<Any> {
        //Comprobar si el jugador existe
        val player = playerService.findPlayerByEmail(email)
        return if (player != null){
            //Actualizar jugador
            val updatedPlayer = playerService.updatePlayer(email,params)
            if (updatedPlayer != null){
                BaseResponse.SuccessResponse(data = updatedPlayer)
            }else{
                BaseResponse.ErrorResponse(message = "Error updating player")
            }
        }else{
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun deletePlayer(id: Int): BaseResponse<Any> {
        //Comprobar si el jugador existe
        val player = playerService.getPlayerById(id)
        return if (player != null){
            //Eliminar jugador
            val deleted = playerService.deletePlayer(id)
            if (deleted){
                BaseResponse.SuccessResponse(data = "Player deleted")
            }else{
                BaseResponse.ErrorResponse(message = "Error deleting player")
            }
        }else{
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    //Primero comprobar si el jugador existe
    private suspend fun isEmailExist(email: String): Boolean {
        return playerService.findPlayerByEmail(email) != null
    }
}