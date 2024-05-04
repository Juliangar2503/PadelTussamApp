package com.backtussam.repositories

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.backtussam.security.JWTConfig
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
                val token = JWTConfig.instance.createToken(player.id)
                player.authToken = token

                // Imprimir el token y las reclamaciones
                println("Created token: $token")
                val decodedJWT = JWT.decode(token)
                println("Issuer: ${decodedJWT.issuer}")
                println("Audience: ${decodedJWT.audience}")
                println("Claim 'id': ${decodedJWT.getClaim(JWTConfig.CLAIM).asInt()}")

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