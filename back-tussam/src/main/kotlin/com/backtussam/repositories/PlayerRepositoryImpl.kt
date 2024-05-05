package com.backtussam.repositories

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.backtussam.security.JWTConfig
import com.backtussam.security.hash
import com.backtussam.services.CreatePlayerParams
import com.backtussam.services.LoginPlayerParams
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
                val token = JWTConfig.instance.createToken(player.id.toString())
                player.authToken = token

                // Imprimir el token y las reclamaciones
                println("Created token: $token")
                val decodedJWT = JWT.decode(token)
                println("Issuer: ${decodedJWT.issuer}")
                println("Audience: ${decodedJWT.audience}")
                println("Claim 'id': ${decodedJWT.getClaim(JWTConfig.CLAIM).asString()}")

                BaseResponse.SuccessResponse(data = player)
            }else{
                BaseResponse.ErrorResponse()
            }
        }
    }

    override suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any> {
        val player = playerService.findPlayerByEmail(params.email)
        val encryptedPassword = playerService.findPasswordByEmail(params.email)
        return if (player != null){
            if (encryptedPassword == hash(params.password)){
                val token = JWTConfig.instance.createToken(player.id.toString())
                player.authToken = token

                // Imprimir el token y las reclamaciones
                println("Created token: $token")
                val decodedJWT = JWT.decode(token)
                println("Issuer: ${decodedJWT.issuer}")
                println("Audience: ${decodedJWT.audience}")
                println("Claim 'id': ${decodedJWT.getClaim(JWTConfig.CLAIM).asString()}")

                BaseResponse.SuccessResponse(data = player)
            }else{
                BaseResponse.ErrorResponse(message = "Invalid password")
            }
        }else{
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    //Primero comprobar si el jugador existe
    private suspend fun isEmailExist(email: String): Boolean {
        return playerService.findPlayerByEmail(email) != null
    }
}