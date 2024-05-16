package com.backtussam.repositories.player

import com.backtussam.security.JWTConfig
import com.backtussam.security.hash
import com.backtussam.services.league.LeagueService
import com.backtussam.services.match.MatchService
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.LoginPlayerParams
import com.backtussam.services.player.PlayerService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.match.CreateMatchParams
import com.backtussam.utils.params.match.ResultMatchParams
import com.backtussam.utils.params.player.UpdatePlayerParams
import kotlin.random.Random

class PlayerRepositoryImpl(
    private val playerService: PlayerService,
    private val leagueService: LeagueService,
    private val matchService: MatchService
) : PlayerRepository {
    /*** ALTA Y BAJA DE JUGADORES ***/
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


    /*** CONSULTAS DE JUGADORES ***/

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

    override suspend fun getPlayersByMatch(matchId: Int): BaseResponse<Any> {
        //Comprobar si el partido está registrado
        //Comprobar si hay jugadores registrados en el partido
        //Devolver los jugadores del partido
        TODO("Not yet implemented")
    }

    /**** ACCIONES DE JUGADORES ****/

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

    override suspend fun openMatch(playerId:Int, type:String): BaseResponse<Any> {
        //Comprobar si el jugador está registrado
        if (playerService.getPlayerById(playerId) == null) {
            return BaseResponse.ErrorResponse(message = "Player not found")
        } else {
            val player = playerService.getPlayerById(playerId)
            //Crear partido con el jugador como primer jugador
            val params = CreateMatchParams(id_player1 = playerId, open = true, type = "Competitive", level = player?.leagueId)
            println("params: ${params}")
            matchService.createMatch(params)
            return  BaseResponse.SuccessResponse(data = "Match created")
        }
    }

    override suspend fun uploadResultMatch(matchId: Int, params: ResultMatchParams): BaseResponse<Any> {
        //Comprobar si el partido existe
        if (matchService.getMatchById(matchId) == null) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            //Actualizar resultado del partido
            matchService.loadResults(matchId, params)
            matchService.calculateResults(matchId)
            return BaseResponse.SuccessResponse(data = "Match updated")
        }
    }

    override suspend fun addPlayerToMatch(playerId: Int, matchId: Int): BaseResponse<Any> {
        if (playerService.getPlayerById(playerId) == null) {
            return BaseResponse.ErrorResponse(message = "Player not found")
        } else {
            if (matchService.getMatchById(matchId) == null) {
                return BaseResponse.ErrorResponse(message = "Match not found")
            } else {
                if (matchService.isPlayerInMatch(playerId, matchId)) {
                    return BaseResponse.ErrorResponse(message = "Player already in match")
                } else {
                    val payersId = matchService.getIdPlayersInMatch(matchId)
                    if (payersId.isNotEmpty() && payersId.contains(playerId)) {
                        return BaseResponse.ErrorResponse(message = "Player already in match")
                    } else {
                        val position = choosePlacesToPlay(matchId, playerId)
                        if (position == -1) {
                            return BaseResponse.ErrorResponse(message = "Match is full")
                        }else{
                            matchService.addPlayerInMatch(playerId, matchId, position)
                            return BaseResponse.SuccessResponse(data = "Player added to match")
                        }
                    }
                }
            }
        }
    }

    override suspend fun removePlayerFromMatch(playerId: Int, matchId: Int): BaseResponse<Any> {
        if (playerService.getPlayerById(playerId) == null) {
            return BaseResponse.ErrorResponse(message = "Player not found")
        } else {
            if (matchService.getMatchById(matchId) == null) {
                return BaseResponse.ErrorResponse(message = "Match not found")
            } else {
                if (!matchService.isPlayerInMatch(playerId, matchId)) {
                    return BaseResponse.ErrorResponse(message = "Player not in match")
                } else {
                    val payersId = matchService.getIdPlayersInMatch(matchId)
                    if (payersId.isNotEmpty() && !payersId.contains(playerId)) {
                        return BaseResponse.ErrorResponse(message = "Player not in match")
                    } else {
                        val position = matchService.getPlaceOfPlayerInMatch(matchId, playerId)
                        println("La posicion del jugador a borrar es " + position)
                        matchService.removePlayerInMatch(playerId, matchId, position)
                        return BaseResponse.SuccessResponse(data = "Player removed from match")
                    }
                }
            }
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

    override suspend fun confirmResultMatchTeamA(matchId: Int, playerId: Int): BaseResponse<Any> {
        val match = matchService.getMatchById(matchId)
        val player = playerService.getPlayerById(playerId)
        if (match == null || match.open || player == null) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            if(match.id_player1 == playerId || match.id_player2 == playerId){
                matchService.confirmOneResults(matchId)
                return BaseResponse.SuccessResponse(data = "Match updated")
            }
            else{
                return BaseResponse.ErrorResponse(message = "Player not eligible to confirm")
            }
        }
    }

    override suspend fun confirmResultMatchTeamB(matchId: Int, playerId: Int): BaseResponse<Any> {
        val match = matchService.getMatchById(matchId)
        val player = playerService.getPlayerById(playerId)
        if (match == null || match.open || player == null) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            if(match.id_player3 == playerId || match.id_player4 == playerId){
                matchService.confirmOneResults(matchId)
                return BaseResponse.SuccessResponse(data = "Match updated")
            }
            else{
                return BaseResponse.ErrorResponse(message = "Player not eligible to confirm")
            }
        }
    }

    override suspend fun choosePlacesToPlay(matchId: Int, playerId: Int): Int {
        val match = matchService.getMatchById(matchId)
        val player = playerService.getPlayerById(playerId)
        val placesFeeList = mutableListOf<Int>()
        if (match == null || !match.open || player == null) {
            return -1
        } else {
            val emptyPositions = mutableListOf<Int>()
            if(match.id_player1 == null){
                emptyPositions.add(1)
            }
            if(match.id_player2 == null){
                emptyPositions.add(2)
            }
            if(match.id_player3 == null){
                emptyPositions.add(3)
            }
            if(match.id_player4 == null){
                emptyPositions.add(4)
            }
            return if(emptyPositions.isNotEmpty()) {
                emptyPositions[Random.nextInt(emptyPositions.size)]
            } else {
                -1
            }
        }
    }


    //Primero comprobar si el jugador existe
    private suspend fun isEmailExist(email: String): Boolean {
        return playerService.findPlayerByEmail(email) != null
    }
}