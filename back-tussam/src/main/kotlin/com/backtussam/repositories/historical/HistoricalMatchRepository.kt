package com.backtussam.repositories.historical

import com.backtussam.services.historical.HistoricalMatchService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.historical.CreateHistoricalMatch

interface HistoricalMatchRepository {
    suspend fun getHistoricalMatch(id: Int): BaseResponse<Any>
    suspend fun getHistoricalMatches(): BaseResponse<Any>
    suspend fun getHistoricalMatchesByPlayer(id: Int): BaseResponse<Any>
    suspend fun registerHistoricalMatch(params: CreateHistoricalMatch): BaseResponse<Any>
    suspend fun updateHistoricalMatch(id: Int, params: CreateHistoricalMatch): BaseResponse<Any>
    suspend fun deleteHistoricalMatch(id: Int): BaseResponse<Any>
}