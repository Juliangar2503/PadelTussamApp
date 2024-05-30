package com.backtussam.routes

import com.backtussam.repositories.player.PlayerRepository
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.LoginPlayerParams
import com.backtussam.utils.params.player.UpdatePlayerParams
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoutes(repository: PlayerRepository){
    routing {
        route("/auth"){
            // http://localhost:8080/auth/register
            post("/register") {
                val params = call.receive<CreatePlayerParams>()
                val result = repository.registerPlayer(params)
                call.respond(result.statusCode, result)

            }
            // http://localhost:8080/auth/login
            post("/login") {
                val params = call.receive<LoginPlayerParams>()
                val result = repository.loginPlayer(params)
                call.respond(result.statusCode, result)
            }
        }
        authenticate {
            route("/player"){
                // http://localhost:8080/player/{email}
                get("/{email}"){
                    val email = call.parameters["email"] ?: ""
                    val result = repository.getPlayerByEmail(email)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/player/{id}
                get ("/findById/{id}"){
                    val id = call.parameters["id"]?.toInt() ?: 0
                    val result = repository.getPlayerById(id)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/player/all
                get("/all"){
                    val result = repository.getPlayers()
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/player/league/{leagueId}
                get("/league/{leagueId}"){
                    val leagueId = call.parameters["leagueId"]?.toInt() ?: 0
                    val result = repository.getPlayersByLeague(leagueId)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/player/name/{name}
                get("/name/{name}"){
                    val name = call.parameters["name"] ?: ""
                    val result = repository.getPlayersByName(name)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/player/{id}/changeLeague/{leagueId}
                put ("{id}/changeLeague/{leagueId}") {
                    val id = call.parameters["id"]?.toInt() ?: 0
                    val leagueId = call.parameters["leagueId"]?.toInt() ?: 0
                    val result = repository.addPlayerToLeague(id, leagueId)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/{email}
                put("/{email}"){
                    val email = call.parameters["email"] ?: ""
                    val params = call.receive<UpdatePlayerParams>()
                    val result = repository.updatePlayer(email, params)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/player/{id}
                delete("/{id}"){
                    val id = call.parameters["id"]?.toInt() ?: 0
                    val result = repository.deletePlayer(id)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/openMatch/{playerId}/{type}
                post ("openMatch/{playerId}/{type}"){
                    val playerId = call.parameters["playerId"]?.toInt() ?: 0
                    val type = call.parameters["type"] ?: ""
                    val result = repository.openMatch(playerId, type)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/{playerId}/addMatch/{matchId}
                put("/{playerId}/addMatch/{matchId}"){
                    val playerId = call.parameters["playerId"]?.toInt() ?: 0
                    val matchId = call.parameters["matchId"]?.toInt() ?: 0
                    val result = repository.addPlayerToMatch(playerId, matchId)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/{playerId}/removeMatch/{matchId}
                put("/{playerId}/removeMatch/{matchId}"){
                    val playerId = call.parameters["playerId"]?.toInt() ?: 0
                    val matchId = call.parameters["matchId"]?.toInt() ?: 0
                    val result = repository.removePlayerFromMatch(playerId, matchId)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/loadResults/{matchId}
                put ("/loadResults/{matchId}"){
                    val matchId = call.parameters["matchId"]?.toInt() ?: 0
                    val result = repository.uploadResultMatch(matchId, call.receive())
                    //Actualizar puntos

                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/confirmResultTeamA/{matchId}/{playerId}
                put ("/confirmResultTeamA/{matchId}/{playerId}"){
                    val matchId = call.parameters["matchId"]?.toInt() ?: 0
                    val playerId = call.parameters["playerId"]?.toInt() ?: 0
                    val result = repository.confirmResultMatchTeamA(matchId, playerId)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/confirmResultTeamB/{matchId}/{playerId}
                put ("/confirmResultTeamB/{matchId}/{playerId}"){
                    val matchId = call.parameters["matchId"]?.toInt() ?: 0
                    val playerId = call.parameters["playerId"]?.toInt() ?: 0
                    val result = repository.confirmResultMatchTeamB(matchId, playerId)
                    call.respond(result.statusCode, result)
                }

                // http://localhost:8080/player/all/{orderField}/{filterField}/{filterValor}
                get("/all/{orderField}/{filterField}/{filterValor}"){
                    val orderField = call.parameters["orderField"] ?: ""
                    val filterField = call.parameters["filterField"] ?: ""
                    val filterValor = call.parameters["filterValor"] ?: ""
                    val result = repository.getGlobalQueryPlayer(orderField, filterField, filterValor)
                    call.respond(result.statusCode, result)
                }
            }
        }

    }

}