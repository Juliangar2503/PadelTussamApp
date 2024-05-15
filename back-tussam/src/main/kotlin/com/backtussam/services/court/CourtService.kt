package com.backtussam.services.court

import com.backtussam.model.Court
import com.backtussam.utils.params.court.CreateCourtParams

interface CourtService {
    suspend fun getCourts(): List<Court?>
    suspend fun getCourtByName(name:String): Court?
    suspend fun createCourt(params:CreateCourtParams): Court?
    suspend fun updateCourt(name: String, params:CreateCourtParams): Court?
    suspend fun deleteCourt(name: String): Boolean
}