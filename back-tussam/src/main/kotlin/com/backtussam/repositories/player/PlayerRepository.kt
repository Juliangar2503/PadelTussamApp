package com.backtussam.repositories.player

import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.LoginPlayerParams
import com.backtussam.utils.BaseResponse


interface PlayerRepository {
    suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any>
    suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any>
}