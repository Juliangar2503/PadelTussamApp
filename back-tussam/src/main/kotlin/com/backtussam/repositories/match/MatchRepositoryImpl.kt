package com.backtussam.repositories.match

import com.backtussam.services.match.MatchService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.match.CreateMatchParams

class MatchRepositoryImpl(
    private val matchService: MatchService
) : MatchRepository {
    override suspend fun getMatches(): BaseResponse<Any> {
        val matches = matchService.getMatches()
        println("MatchRepository -> getMatches -> matches: $matches")
        return if (matches.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matches)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun getMatchById(id: Int): BaseResponse<Any> {
        if (!isMatchExist(id)) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            val match = matchService.getMatchById(id)
            return BaseResponse.SuccessResponse(data = match)
        }

    }

    override suspend fun getMatchesByLeague(leagueId: Int): BaseResponse<Any> {
        val matchesCompetitives = matchService.getMatchesByLeague(leagueId)
        println("MatchRepository -> getMatchesByLeague -> matchesCompetitives: $matchesCompetitives")
//        val matchesByLeague = matchesCompetitives.filter { it?.level == leagueId }
        return if (matchesCompetitives.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matchesCompetitives)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun getMatchesByType(typoMatch: String): BaseResponse<Any> {
        val matches = matchService.getMatchesByType(typoMatch)
        return if (matches.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matches)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun getMatchesOpenByPlayer(playerId: Int): BaseResponse<Any> {
        val matches = matchService.getMatchesOpenByPlayer(playerId)
        return if (matches.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matches)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun getMatchesCloseByPlayer(playerId: Int): BaseResponse<Any> {
        val matches = matchService.getMatchesCloseByPlayer(playerId)
        return if (matches.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matches)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun createMatch(params: CreateMatchParams): BaseResponse<Any> {
        val match = matchService.createMatch(params)
        return if (match != null) {
            BaseResponse.SuccessResponse(data = match)
        } else {
            BaseResponse.ErrorResponse(message = "Error creating match")
        }
    }

    override suspend fun updateMatch(id: Int, params: CreateMatchParams): BaseResponse<Any> {
        if (!isMatchExist(id)) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            val match = matchService.updateMatch(id, params)
            return BaseResponse.SuccessResponse(data = match)
        }
    }

    override suspend fun deleteMatch(id: Int): BaseResponse<Any> {
        if (!isMatchExist(id)) {
            return BaseResponse.ErrorResponse(message = "Match not found")
        } else {
            val result = matchService.deleteMatch(id)
            return if (result) {
                BaseResponse.SuccessResponse(data = "Match deleted")
            } else {
                BaseResponse.ErrorResponse(message = "Error deleting match")
            }
        }
    }


    private suspend fun isMatchExist(id: Int): Boolean {
        return matchService.getMatchById(id) != null
    }
}