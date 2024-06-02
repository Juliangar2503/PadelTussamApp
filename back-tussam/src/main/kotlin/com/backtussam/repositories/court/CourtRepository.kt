package com.backtussam.repositories.court

import com.backtussam.model.Court
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.court.CreateCourtParams

interface CourtRepository {
    suspend fun getCourts(): BaseResponse<Any>
    suspend fun getCourtByName(name:String):  BaseResponse<Any>
    suspend fun getCourtById(id: Int):  BaseResponse<Any>
    suspend fun createCourt(name:String):  BaseResponse<Any>
    suspend fun updateCourt(name: String, params:CreateCourtParams):  BaseResponse<Any>
    suspend fun deleteCourt(name: String): BaseResponse<Any>
}