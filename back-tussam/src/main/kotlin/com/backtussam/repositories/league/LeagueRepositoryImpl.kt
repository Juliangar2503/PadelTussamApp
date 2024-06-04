package com.backtussam.repositories.league

import com.backtussam.services.league.LeagueService
import com.backtussam.services.player.PlayerService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.extensions.toLocalDateTime
import com.backtussam.utils.params.league.CreateLeagueParams
import java.time.LocalDateTime

class LeagueRepositoryImpl (
    private val leagueService: LeagueService,
    private val playerService: PlayerService
) : LeagueRepository{
    override suspend fun getLeague(name: String): BaseResponse<Any> {
        // Comprobar si la liga existe
        val league = leagueService.getLeague(name)
        return if (league != null){
            // Liga encontrada
            BaseResponse.SuccessResponse(data = league)
        }else{
            // Liga no encontrada
            BaseResponse.ErrorResponse(message = "League not found")
        }
    }

    override suspend fun getLeagues(): BaseResponse<Any> {
        // Obtener todas las ligas
        val leagues = leagueService.getLeagues()
        return if (leagues.isNotEmpty()){
            // Ligas encontradas
            BaseResponse.SuccessResponse(data = leagues)
        }else{
            // No hay ligas
            BaseResponse.ErrorResponse(message = "No leagues found")
        }
    }

    override suspend fun getLeagueById(id: Int): BaseResponse<Any> {
        // Comprobar si la liga existe
        val league = leagueService.getLeagueById(id)
        return if (league != null){
            // Liga encontrada
            BaseResponse.SuccessResponse(data = league)
        }else{
            // Liga no encontrada
            BaseResponse.ErrorResponse(message = "League not found")
        }
    }

    override suspend fun registerLeagueByName(name: String): BaseResponse<Any> {
        return if (isLeagueExist(name)){
            BaseResponse.ErrorResponse(message = "League already registered")
        }else{
            // Registrar liga
            val league = leagueService.registerLeagueByName(name)
            if (league != null){
                // Responder con el jugador y el token creado
                BaseResponse.SuccessResponse(data = league)
            }else{
                BaseResponse.ErrorResponse(message = "Error creating league")
            }
        }
    }

    override suspend fun registerLeague(params: CreateLeagueParams): BaseResponse<Any> {
        return if (isLeagueExist(params.name)){
            BaseResponse.ErrorResponse(message = "League already registered")
        }else{
            // Registrar liga
            val league = leagueService.registerLeague(params)
            if (league != null){
                // Responder con el jugador y el token creado
                BaseResponse.SuccessResponse(data = league)
            }else{
                BaseResponse.ErrorResponse(message = "Error creating league")
            }
        }
    }

    override suspend fun updateLeague(name: String, params: CreateLeagueParams): BaseResponse<Any> {
        return if (isLeagueExist(name)){
            // Actualizar liga
            val league = leagueService.updateLeague(name, params)
            if (league != null){
                // Liga actualizada
                BaseResponse.SuccessResponse(data = league)
            }else{
                // Error al actualizar liga
                BaseResponse.ErrorResponse(message = "Error updating league")
            }
        }else{
            // Liga no encontrada
            BaseResponse.ErrorResponse(message = "League not found")
        }
    }

    override suspend fun deleteLeague(name: String): BaseResponse<Any> {
        return if (isLeagueExist(name)){
            val league = leagueService.getLeague(name)
            val players = playerService.getPlayersByLeague(league?.id ?: 0)
            if (players.isEmpty()) {
                val deleted = leagueService.deleteLeague(name)
                if (deleted) {
                    BaseResponse.SuccessResponse(data = "League deleted")
                } else {
                    BaseResponse.ErrorResponse(message = "Error deleting league")
                }
            } else {
                BaseResponse.ErrorResponse(message = "No se puede eliminar la liga si hay jugadores en ella")
            }
        }else{
            BaseResponse.ErrorResponse(message = "League not found")
        }
    }

    //Primero comprobar si la liga existe
    private suspend fun isLeagueExist(email: String): Boolean {
        return leagueService.getLeague(email) != null
    }
}