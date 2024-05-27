package com.backtussam.repositories.historical

import com.backtussam.services.historical.HistoricalMatchService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.historical.CreateHistoricalMatch

class HistoricalMatchRepositoryImpl (
    private val historicalMatchService: HistoricalMatchService
): HistoricalMatchRepository {
    override suspend fun getHistoricalMatch(id: Int): BaseResponse<Any> {
        val matchHistorical = historicalMatchService.getHistoricalMatch(id)
        return if (matchHistorical != null) {
            BaseResponse.SuccessResponse(data = matchHistorical)
        } else {
            BaseResponse.ErrorResponse(message = "Match not found")
        }
    }

    override suspend fun getHistoricalMatches(): BaseResponse<Any> {
        val matchesHistorical = historicalMatchService.getHistoricalMatches()
        return if (matchesHistorical.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matchesHistorical)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun getHistoricalMatchesByPlayer(id: Int): BaseResponse<Any> {
        val matchesHistorical = historicalMatchService.getHistoricalMatchesByPlayer(id)
        return if (matchesHistorical.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = matchesHistorical)
        } else {
            BaseResponse.ErrorResponse(message = "No matches found")
        }
    }

    override suspend fun registerHistoricalMatch(params: CreateHistoricalMatch): BaseResponse<Any> {
        val matchHistorical = historicalMatchService.registerHistoricalMatch(params)
        return if (matchHistorical != null) {
            BaseResponse.SuccessResponse(data = matchHistorical)
        } else {
            BaseResponse.ErrorResponse(message = "Error creating match")
        }
    }

    override suspend fun updateHistoricalMatch(id: Int, params: CreateHistoricalMatch): BaseResponse<Any> {
        val matchHistorical = historicalMatchService.updateHistoricalMatch(id, params)
        return if (matchHistorical != null) {
            BaseResponse.SuccessResponse(data = matchHistorical)
        } else {
            BaseResponse.ErrorResponse(message = "Error updating match")
        }
    }

    override suspend fun deleteHistoricalMatch(id: Int): BaseResponse<Any> {
        val isDeleted = historicalMatchService.deleteHistoricalMatch(id)
        return if (isDeleted) {
            BaseResponse.SuccessResponse(data = isDeleted)
        } else {
            BaseResponse.ErrorResponse(message = "Error deleting match")
        }
    }
}