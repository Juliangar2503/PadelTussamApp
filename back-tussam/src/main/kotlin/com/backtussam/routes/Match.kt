package com.backtussam.routes

import com.backtussam.repositories.match.MatchRepository
import com.backtussam.utils.params.match.CreateMatchParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.matchesRoutes(repository: MatchRepository) {
    routing {
        route("/matches") {
            // http://localhost:8080/matches/all
            get("/all") {
                val result = repository.getMatches()
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/matches/find/{id}
            get("/find/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: 0
                val result = repository.getMatchById(id)
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/matches/create
            post("/create") {
                val params = call.receive<CreateMatchParams>()
                val result = repository.createMatch(params)
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/matches/update/{id}
            put("/update/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: 0
                val params = call.receive<CreateMatchParams>()
                val result = repository.updateMatch(id, params)
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/matches/delete/{id}
            delete("/delete/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: 0
                val result = repository.deleteMatch(id)
                call.respond(result.statusCode, result)
            }
        }
    }
}