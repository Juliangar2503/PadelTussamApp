package com.backtussam.repositories

import com.backtussam.services.CreatePlayerParams
import com.backtussam.services.LoginPlayerParams
import com.backtussam.utils.BaseResponse


interface PlayerRepository {
    suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any>
    suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any>
}