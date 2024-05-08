package com.backtussam.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    // JWTConfig
    JWTConfig.initialize("root")

    install(Authentication) {
        jwt {
            // Configurar realm
            realm ="com.backtussam"
            // Verificar token
            verifier(JWTConfig.instance.verifier)
            // Validar token
            validate {
                // Obtener claim
                val claim = it.payload.getClaim(JWTConfig.CLAIM).asString()
                // Si claim no es nulo crear principal
                if(claim != null){
                    // Crear principal
                    UserIdPrincipalForPlayer(claim)
                } else {
                    null
                }
            }
        }
    }
}