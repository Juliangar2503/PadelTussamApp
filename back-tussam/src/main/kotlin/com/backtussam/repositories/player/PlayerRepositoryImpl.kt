package com.backtussam.repositories.player

import com.backtussam.security.JWTConfig
import com.backtussam.security.hash
import com.backtussam.services.historical.HistoricalMatchService
import com.backtussam.services.league.LeagueService
import com.backtussam.services.match.MatchService
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.LoginPlayerParams
import com.backtussam.services.player.PlayerService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.historical.CreateHistoricalMatch
import com.backtussam.utils.params.match.CreateMatchParams
import com.backtussam.utils.params.match.ResultMatchParams
import com.backtussam.utils.params.player.UpdatePlayerParams
import kotlin.random.Random

class PlayerRepositoryImpl(
    private val playerService: PlayerService,
    private val leagueService: LeagueService,
    private val matchService: MatchService,
    private val historicalMatchService: HistoricalMatchService
) : PlayerRepository {
    /*** ALTA Y BAJA DE JUGADORES ***/
    override suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any> {
        return if (isEmailExist(params.email)) {
            BaseResponse.ErrorResponse(message = "Email already registered")
        } else {
            // Registrar jugador
            val player = playerService.registerPlayer(params)
            if (player != null) {
                // Crear token
                val token = JWTConfig.instance.createToken(player.id.toString())
                player.authToken = token

                // Responder con el jugador y el token creado
                BaseResponse.SuccessResponse(data = player)
            } else {
                BaseResponse.ErrorResponse(message = "Error creating player")
            }
        }
    }

    override suspend fun deletePlayer(id: Int): BaseResponse<Any> {
        //Comprobar si el jugador existe
        val player = playerService.getPlayerById(id)
        return if (player != null) {
            //Eliminar jugador
            val deleted = playerService.deletePlayer(id)
            if (deleted) {
                BaseResponse.SuccessResponse(data = "Player deleted")
            } else {
                BaseResponse.ErrorResponse(message = "Error deleting player")
            }
        } else {
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun addPlayerToLeague(playerId: Int, leagueId: Int): BaseResponse<Any> {
        // Comprobar si el jugador existe
        val player = playerService.getPlayerById(playerId)
        return if (player != null) {
            // Comprobar si la liga está registrada
            val league = leagueService.getLeagueById(leagueId)
            if (league != null) {
                // Añadir jugador a la liga
                val added = playerService.addPlayerToLeague(playerId, leagueId)
                if (added) {
                    BaseResponse.SuccessResponse(data = "Player added to league")
                } else {
                    BaseResponse.ErrorResponse(message = "Error adding player to league")
                }
            } else {
                BaseResponse.ErrorResponse(message = "League not found")
            }
        } else {
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }


    /*** CONSULTAS DE JUGADORES ***/

    override suspend fun getPlayerByEmail(email: String): BaseResponse<Any> {
        val player = playerService.findPlayerByEmail(email)
        return if (player != null) {
            BaseResponse.SuccessResponse(data = player)
        } else {
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun getPlayerById(id: Int): BaseResponse<Any> {
        val player = playerService.getPlayerById(id)
        return if (player != null) {
            BaseResponse.SuccessResponse(data = player)
        } else {
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun getPlayers(): BaseResponse<Any> {
        //Comprobar si hay jugadores registrados
        val players = playerService.getPlayers()
        return if (players.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = players)
        } else {
            BaseResponse.ErrorResponse(message = "No players found")
        }
    }

    override suspend fun getPlayersByLeague(leagueId: Int): BaseResponse<Any> {
        //Comprobar si la liga está registrada
        val league = leagueService.getLeagueById(leagueId)
        return if (league != null) {
            //Comprobar si hay jugadores registrados en la liga
            val players = playerService.getPlayersByLeague(leagueId)
            if (players.isNotEmpty()) {
                BaseResponse.SuccessResponse(data = players)
            } else {
                BaseResponse.ErrorResponse(message = "No players found in league")
            }
        } else {
            BaseResponse.ErrorResponse(message = "League not found")
        }
    }

    override suspend fun getPlayersByName(name: String): BaseResponse<Any> {
        //Comprobar si hay jugadores registrados con ese nombre
        val players = playerService.getPlayersByName(name)
        return if (players.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = players)
        } else {
            BaseResponse.ErrorResponse(message = "No players found with that name")
        }
    }

    override suspend fun getPlayersByMatch(matchId: Int): BaseResponse<Any> {
        //Comprobar si el partido está registrado
        //Comprobar si hay jugadores registrados en el partido
        //Devolver los jugadores del partido
        TODO("Not yet implemented")
    }

    override suspend fun getGlobalQueryPlayer(
        orderField: String,
        filterField: String,
        filterValor: String
    ): BaseResponse<Any> {
        val players = playerService.getPlayers()
        if (players.isNotEmpty()) {
            val filteredPlayers = when (filterField) {
                "name" -> players.filter { it.name.startsWith(filterValor) }
                "lastName" -> players.filter { it.lastName == filterValor }
                "email" -> players.filter { it.email == filterValor }
                "location" -> players.filter { it.location == filterValor }
                "nickname" -> players.filter { it.nickname == filterValor }
                "active" -> players.filter { it.active.toString() == filterValor }
                "leagueId" -> players.filter { it.leagueId.toString() == filterValor }
                "roleId" -> players.filter { it.roleId.toString() == filterValor }
                "points" -> players.filter { it.points.toString() == filterValor }
                "createdAt" -> players.filter { it.createdAt == filterValor }
                else -> players
            }
            val sortedPlayers = when (orderField) {
                "name" -> filteredPlayers.sortedBy { it.name }
                "lastName" -> filteredPlayers.sortedBy { it.lastName }
                "email" -> filteredPlayers.sortedBy { it.email }
                "location" -> filteredPlayers.sortedBy { it.location }
                "nickname" -> filteredPlayers.sortedBy { it.nickname }
                "active" -> filteredPlayers.sortedBy { it.active }
                "leagueId" -> filteredPlayers.sortedBy { it.leagueId }
                "roleId" -> filteredPlayers.sortedBy { it.roleId }
                "points" -> filteredPlayers.sortedByDescending { it.points }
                "createdAt" -> filteredPlayers.sortedBy { it.createdAt }
                else -> filteredPlayers
            }
            return BaseResponse.SuccessResponse(data = sortedPlayers)
        } else {
            return BaseResponse.ErrorResponse(message = "No players found")
        }
    }


    /**** ACCIONES DE JUGADORES ****/

    override suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any> {
        // Comprobar si el jugador existe
        val player = playerService.findPlayerByEmail(params.email)
        // Comprobar si la contraseña es correcta
        val encryptedPassword = playerService.findPasswordByEmail(params.email)
        // Comprobar si el jugador existe y la contraseña es correcta
        return if (player != null) {
            if (encryptedPassword == hash(params.password)) {
                // Crear token
                val token = JWTConfig.instance.createToken(player.id.toString())
                player.authToken = token
                // Jugador encontrado y contraseña correcta
                BaseResponse.SuccessResponse(data = player, message = "Bienvenido ${player.name}")
            } else {
                // Contraseña incorrecta
                BaseResponse.ErrorResponse(message = "Invalid password")
            }
        } else {
            // Jugador no encontrado
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun openMatch(playerId: Int, type: String): BaseResponse<Any> {
        //Comprobar si el jugador está registrado
        if (playerService.getPlayerById(playerId) == null) {
            return BaseResponse.ErrorResponse(message = "Player not found")
        } else {
            val player = playerService.getPlayerById(playerId)
            //Crear partido con el jugador como primer jugador
            val params =
                CreateMatchParams(id_player1 = playerId, open = true, type = type, level = player?.leagueId)
            matchService.createMatch(params)
            return BaseResponse.SuccessResponse(data = "Match created")
        }
    }

    override suspend fun uploadResultMatch(matchId: Int, params: ResultMatchParams): BaseResponse<Any> {
        //Comprobar si el partido existe
        if (matchService.getMatchById(matchId) == null) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            //Actualizar resultado del partido
            if (matchService.getIdPlayersInMatch(matchId).size != 4) {
                return BaseResponse.ErrorResponse(message = "Match is not full")
            } else {
                matchService.loadResults(matchId, params)

                matchService.calculateResults(matchId)
                return BaseResponse.SuccessResponse(data = "Match updated")
            }
        }
    }

    override suspend fun addPlayerToMatch(playerId: Int, matchId: Int): BaseResponse<Any> {
        if (playerService.getPlayerById(playerId) == null) {
            return BaseResponse.ErrorResponse(message = "Player not found")
        } else {
            if (matchService.getMatchById(matchId) == null) {
                return BaseResponse.ErrorResponse(message = "Match not found")
            } else {
                var payersId = matchService.getIdPlayersInMatch(matchId)
                if (payersId.isNotEmpty() && payersId.contains(playerId)) {
                    return BaseResponse.ErrorResponse(message = "Player already in match2")
                } else {
                    val position = choosePlacesToPlay(matchId, playerId)
                    if (position == -1) {
                        return BaseResponse.ErrorResponse(message = "Match is full")
                    } else {
                        matchService.addPlayerInMatch(playerId, matchId, position)
                        payersId = matchService.getIdPlayersInMatch(matchId)
                        if (payersId.size == 4) {
                            matchService.changeOpenState(matchId, false)
                        }
                        return BaseResponse.SuccessResponse(data = "Player added to match")
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
                val payersId = matchService.getIdPlayersInMatch(matchId)
                if (payersId.isNotEmpty() && !payersId.contains(playerId)) {
                    return BaseResponse.ErrorResponse(message = "Player not in match")
                } else {
                    val position = matchService.getPlaceOfPlayerInMatch(matchId, playerId)
                    println("La posicion del jugador a borrar es " + position)
                    matchService.removePlayerInMatch(playerId, matchId, position)
                    matchService.changeOpenState(matchId, true)
                    if (matchService.getIdPlayersInMatch(matchId).isEmpty()) {
                        matchService.deleteMatch(matchId)
                    }
                    return BaseResponse.SuccessResponse(data = "Player removed from match")
                }
            }
        }

    }

    override suspend fun updatePlayer(email: String, params: UpdatePlayerParams): BaseResponse<Any> {
        //Comprobar si el jugador existe
        val player = playerService.findPlayerByEmail(email)
        return if (player != null) {
            //Actualizar jugador
            val updatedPlayer = playerService.updatePlayer(email, params)
            if (updatedPlayer != null) {
                BaseResponse.SuccessResponse(data = updatedPlayer)
            } else {
                BaseResponse.ErrorResponse(message = "Error updating player")
            }
        } else {
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    override suspend fun confirmResultMatchTeamA(matchId: Int, playerId: Int): BaseResponse<Any> {
        val match = matchService.getMatchById(matchId)
        val player = playerService.getPlayerById(playerId)
        if (match == null || match.open || player == null ) {
            return BaseResponse.ErrorResponse(message = "No se ha encontrado el partido o no es competitivo")
        } else {
            if (match.id_player1 == playerId || match.id_player2 == playerId) {
                checkMatch(matchId)
                return BaseResponse.SuccessResponse(data = "Match updated")
            } else {
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
            if (match.id_player3 == playerId || match.id_player4 == playerId) {
                matchService.confirmSecondResults(matchId)
                //Si ambos equipos han confirmado el resultado, se actualizan los puntos
                earnPointsAfterCheckMatch(matchId)
                //Si ambos equipos han confirmado se guarda el paritdo en el historico
                saveMatchInHistory(matchId, match.id_player1!!)
                saveMatchInHistory(matchId, match.id_player2!!)
                saveMatchInHistory(matchId, match.id_player3!!)
                saveMatchInHistory(matchId, match.id_player4!!)
                return BaseResponse.SuccessResponse(data = "Match updated")
            } else {
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
            if (match.id_player1 == null) {
                emptyPositions.add(1)
            }
            if (match.id_player2 == null) {
                emptyPositions.add(2)
            }
            if (match.id_player3 == null) {
                emptyPositions.add(3)
            }
            if (match.id_player4 == null) {
                emptyPositions.add(4)
            }
            return if (emptyPositions.isNotEmpty()) {
                emptyPositions[Random.nextInt(emptyPositions.size)]
            } else {
                -1
            }
        }
    }

    override suspend fun earnPointsAfterCheckMatch(matchId: Int): BaseResponse<Any> {
        val match = matchService.getMatchById(matchId)
        if (match == null) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            if (match.confirmResult1 && match.confirmResult2) {
                val player1 = playerService.getPlayerById(match.id_player1!!)
                val player2 = playerService.getPlayerById(match.id_player2!!)
                val player3 = playerService.getPlayerById(match.id_player3!!)
                val player4 = playerService.getPlayerById(match.id_player4!!)
                val pointsWiner = 5
                val pointsLouser = 2
                if (player1 == null || player2 == null || player3 == null || player4 == null) {
                    return BaseResponse.ErrorResponse(message = "Players not found")
                } else {
                    if (match.matchResult!!.count { it == 'A' } > match.matchResult.count { it == 'B' }) {
                        player1.points += pointsWiner
                        player2.points += pointsWiner
                        player3.points += pointsLouser
                        player4.points += pointsLouser
                    } else {
                        player1.points += pointsLouser
                        player2.points += pointsLouser
                        player3.points += pointsWiner
                        player4.points += pointsWiner
                    }
                    println("Puntos de los jugadores: ${player1.points}, ${player2.points}, ${player3.points}, ${player4.points}")

                    playerService.earnPoints(player1.id, player1.points)
                    playerService.earnPoints(player2.id, player2.points)
                    playerService.earnPoints(player3.id, player3.points)
                    playerService.earnPoints(player4.id, player4.points)

                }

            }
            return BaseResponse.SuccessResponse(data = "Points earned")
        }
    }

    override suspend fun saveMatchInHistory(matchId: Int, playerId: Int): BaseResponse<Any> {
        val match = matchService.getMatchById(matchId)
        val player = playerService.getPlayerById(playerId)
        if (match == null || player == null) {
            return BaseResponse.ErrorResponse(message = "Match or player not found")
        } else {
            val params = CreateHistoricalMatch(
                id_player = playerId,
                id_match = matchId,
                score = match.matchResult.toString(),
                partner = getParnet(matchId, playerId)!!,
                name_league = getNameLeague(matchId)!!,
                isWin = checkWhoseWin(matchId, playerId)
            )
            val historicalMatch = historicalMatchService.registerHistoricalMatch(params)
            return if (historicalMatch != null) {
                BaseResponse.SuccessResponse(data = historicalMatch)
            } else {
                BaseResponse.ErrorResponse(message = "Error creating historical match")
            }
        }
    }

    private suspend fun getGamesA(idMach: Int) : Int{
        val match = matchService.getMatchById(idMach)
        if (match != null) {
           val totalGamesA = match.scoreSet1A + match.scoreSet2A + match.scoreSet3A
            return totalGamesA
        }else{
            return 0
        }
    }

    private suspend fun getGamesB(idMach: Int) : Int{
        val match = matchService.getMatchById(idMach)
        if (match != null) {
            val totalGamesB = match.scoreSet1B + match.scoreSet2B + match.scoreSet3B
            return totalGamesB
        }else{
            return 0
        }
    }

    private suspend fun addGameToPlayer(matchId: Int, playerId: Int){
        val player = playerService.getPlayerById(playerId)
        val match = matchService.getMatchById(matchId)
        if (player != null) {
            if (match != null) {
                if (match.id_player1 == playerId || match.id_player2 == playerId) {
                    playerService.winGames(playerId, getGamesA(matchId))
                    playerService.loseGames(playerId, getGamesB(matchId))
                    playerService.differenceGames(playerId, getGamesA(matchId) - getGamesB(matchId))
                } else {
                    playerService.winGames(playerId, getGamesB(matchId))
                    playerService.loseGames(playerId, getGamesA(matchId))
                    playerService.differenceGames(playerId, getGamesB(matchId) - getGamesA(matchId))
                }
            }
        }
    }

    private suspend fun getParnet(matchId: Int, playerId: Int): String? {
        val match = matchService.getMatchById(matchId)
        //Obtener el id del juagdor pareja
        return if (match != null) {
            if (match.id_player1 == playerId) {
                playerService.getPlayerById(match.id_player2!!)?.name
            } else if (match.id_player2 == playerId) {
                playerService.getPlayerById(match.id_player1!!)?.name
            } else if (match.id_player3 == playerId) {
                playerService.getPlayerById(match.id_player4!!)?.name
            } else if (match.id_player4 == playerId) {
                playerService.getPlayerById(match.id_player3!!)?.name
            } else {
                null
            }
        } else {
            null
        }
    }

    private suspend fun getNameLeague(matchId: Int): String? {
        val match = matchService.getMatchById(matchId)
        return if (match != null) {
            leagueService.getLeagueById(match.level!!)?.name
        } else {
            null
        }
    }

    private suspend fun checkWhoseWin(matchId: Int, playerId: Int): Boolean {
        val match = matchService.getMatchById(matchId)
        return if (match != null) {
            if (match.id_player1 == playerId || match.id_player2 == playerId) {
                match.matchResult!!.count { it == 'A' } > match.matchResult.count { it == 'B' }
            } else {
                match.matchResult!!.count { it == 'B' } > match.matchResult.count { it == 'A' }
            }
        } else {
            false
        }
    }

    private suspend fun checkMatch(matchId: Int){
        val match = matchService.getMatchById(matchId)
        matchService.confirmOneResults(matchId)
        if(match?.type == "Competitive") {
            //Si ambos equipos han confirmado el resultado, se actualizan los puntos
            earnPointsAfterCheckMatch(matchId)
            //Si ambos equipos han confirmado se guarda el paritdo en el historico
            saveMatchInHistory(matchId, match.id_player1!!)
            saveMatchInHistory(matchId, match.id_player2!!)
            saveMatchInHistory(matchId, match.id_player3!!)
            saveMatchInHistory(matchId, match.id_player4!!)
            //Se actualizan los juegos ganados, perdidos y diferencia de juegos
            addGameToPlayer(matchId, match.id_player1)
            addGameToPlayer(matchId, match.id_player2)
            addGameToPlayer(matchId, match.id_player3)
            addGameToPlayer(matchId, match.id_player4)

        }

    }

    //Primero comprobar si el jugador existe
    private suspend fun isEmailExist(email: String): Boolean {
        return playerService.findPlayerByEmail(email) != null
    }
}