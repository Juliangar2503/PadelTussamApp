package com.backtussam.repositories

import com.auth0.jwt.JWT
import com.backtussam.security.JWTConfig
import com.backtussam.security.hash
import com.backtussam.utils.params.CreatePlayerParams
import com.backtussam.utils.params.LoginPlayerParams
import com.backtussam.services.PlayerService
import com.backtussam.utils.BaseResponse

class PlayerRepositoryImpl(
    private val playerService: PlayerService
) : PlayerRepository {
    // Comprobar si el email ya está registrado
    override suspend fun registerPlayer(params: CreatePlayerParams): BaseResponse<Any> {
        return if (isEmailExist(params.email)){
            BaseResponse.ErrorResponse(message = "Email already registered")
        }else{
            // Registrar jugador
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
                BaseResponse.ErrorResponse(message = "Error creating player")
            }
        }
    }

    override suspend fun loginPlayer(params: LoginPlayerParams): BaseResponse<Any> {
        // Comprobar si el jugador existe
        val player = playerService.findPlayerByEmail(params.email)
        // Comprobar si la contraseña es correcta
        val encryptedPassword = playerService.findPasswordByEmail(params.email)
        // Comprobar si el jugador existe y la contraseña es correcta
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
                // Contraseña incorrecta
                BaseResponse.ErrorResponse(message = "Invalid password")
            }
        }else{
            // Jugador no encontrado
            BaseResponse.ErrorResponse(message = "Player not found")
        }
    }

    //Primero comprobar si el jugador existe
    private suspend fun isEmailExist(email: String): Boolean {
        return playerService.findPlayerByEmail(email) != null
    }
}