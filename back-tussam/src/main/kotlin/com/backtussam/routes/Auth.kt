package com.backtussam.routes

import com.backtussam.repositories.PlayerRepository
import com.backtussam.services.CreatePlayerParams
import com.backtussam.services.LoginPlayerParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoutes(repository: PlayerRepository){
    routing {
        route("/auth"){
            post("/register") {
                val params = call.receive<CreatePlayerParams>()
                val result = repository.registerPlayer(params)
                call.respond(result.statusCode, result)
            }
            post("/login") {
                val params = call.receive<LoginPlayerParams>()
                val result = repository.loginPlayer(params)
                call.respond(result.statusCode, result)
            }

        }
    }
}