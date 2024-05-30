package com.backtussam.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JWTConfig private constructor(secret: String){

    private val algorithm = Algorithm.HMAC256(secret)

    // Verificar token
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    // Crear token
    fun createToken(id: String): String = JWT
        .create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim(CLAIM, id)
        .withExpiresAt(Date(System.currentTimeMillis() + 3600000) ) // 1 hora
        .sign(algorithm)


    companion object {
        private const val ISSUER = "root"
        private const val AUDIENCE = "root"
        const val CLAIM = "id"

        lateinit var instance: JWTConfig
            private set

        // Inicializar JWTConfig
        fun initialize(secret: String){
            synchronized(this){
                if(!this::instance.isInitialized){
                    instance = JWTConfig(secret)
                }
            }
        }
    }


}