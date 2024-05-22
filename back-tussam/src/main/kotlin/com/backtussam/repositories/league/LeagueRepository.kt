package com.backtussam.repositories.league

import com.backtussam.model.League
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.league.CreateLeagueParams

interface LeagueRepository {
    /********** OBTENER LIGAS **********/
    suspend fun getLeague(name: String): BaseResponse<Any>
    suspend fun getLeagues(): BaseResponse<Any>
    suspend fun getLeagueById(id: Int): BaseResponse<Any>

    suspend fun registerLeagueByName(name: String): BaseResponse<Any>
    suspend fun registerLeague(params: CreateLeagueParams): BaseResponse<Any>
    suspend fun updateLeague(name: String, params: CreateLeagueParams): BaseResponse<Any>
    suspend fun deleteLeague(name: String): BaseResponse<Any>
}