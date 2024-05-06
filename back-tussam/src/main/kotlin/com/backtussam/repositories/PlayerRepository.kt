package com.backtussam.repositories

import com.backtussam.utils.params.CreatePlayerParams
import com.backtussam.utils.params.LoginPlayerParams
import com.backtussam.utils.BaseResponse


interface PlayerRepository {
    suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any>
    suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any>
}