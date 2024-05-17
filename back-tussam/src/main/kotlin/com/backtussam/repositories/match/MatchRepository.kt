package com.backtussam.repositories.match

import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.match.CreateMatchParams

interface MatchRepository {
    suspend fun getMatches(): BaseResponse<Any>
    suspend fun getMatchById(id: Int): BaseResponse<Any>
    suspend fun getMatchesByLeague(leagueId: Int): BaseResponse<Any>
    suspend fun getMatchesByType(typoMatch:String): BaseResponse<Any>
    suspend fun createMatch(params: CreateMatchParams): BaseResponse<Any>
    suspend fun updateMatch(id: Int, params:CreateMatchParams): BaseResponse<Any>
    suspend fun deleteMatch(id: Int): BaseResponse<Any>
}