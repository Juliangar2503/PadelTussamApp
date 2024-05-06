package com.backtussam.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    // JWTConfig
    JWTConfig.initialize("root")

    install(Authentication) {
        jwt {
            realm ="com.backtussam"
            verifier(JWTConfig.instance.verifier)
            validate {
                println("All claims: ${it.payload.claims}")
                //val claim = it.payload.getClaim("id").asString()
                val claim = it.payload.getClaim(JWTConfig.CLAIM).asString()
                println("JWT token: ${it.payload}")
                println("Claim 'id': $claim")
                if(claim != null){
                    UserIdPrincipalForPlayer(claim)
                } else {
                    null
                }
            }
        }
    }
}