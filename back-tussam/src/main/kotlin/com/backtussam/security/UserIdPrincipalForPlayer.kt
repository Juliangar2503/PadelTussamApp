package com.backtussam.security

import io.ktor.server.auth.*

// Principal para el jugador
// Utiliza para representar la identidad de un jugador en el contexto de autenticación
data class UserIdPrincipalForPlayer(val id:String): Principal