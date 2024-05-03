package com.backtussam.repositories

import com.backtussam.services.CreatePlayerParams
import com.backtussam.services.PlayerService
import com.backtussam.utils.BaseResponse

class PlayerRepositoryImpl(
    private val playerService: PlayerService
) : PlayerRepository {
    override suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any> {
        return if (isEmailExist(params.email)){
            BaseResponse.ErrorResponse(message = "Email already registered")
        }else{
            val player = playerService.registerPlayer(params)
            if (player != null){
                //val token = JWTConfig.instance.createToken(user.id.toString())
                //user.authToken = token
                BaseResponse.SuccessResponse(data = player)
            }else{
                BaseResponse.ErrorResponse()
            }
        }
    }

    override suspend fun loginPlayer(email: String, password: String): BaseResponse<Any> {
        TODO("Not yet implemented")
    }

    //Primero comprobar si el jugador existe
    private suspend fun isEmailExist(email: String): Boolean {
        return playerService.findPlayerByEmail(email) != null
    }
}