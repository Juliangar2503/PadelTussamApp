package com.backtussam.routes

import com.backtussam.repositories.league.LeagueRepository
import com.backtussam.utils.params.league.CreateLeagueParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.leagueRoutes(repository: LeagueRepository){
    routing {
        route("/league"){
            // http://localhost:8080/league/all
            get ("/all"){
                val result = repository.getLeagues()
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/league/find/{name}
            get ("/find/{name}"){
                val name = call.parameters["name"] ?: ""
                val result = repository.getLeague(name)
                call.respond(result.statusCode, result)
            }
            get("/findById/{id}"){
                val id = call.parameters["id"]?.toInt() ?: 0
                val result = repository.getLeagueById(id)
                call.respond(result.statusCode, result)
            }

            // http://localhost:8080/league/create
            post("/create") {
                val params = call.receive<CreateLeagueParams>()
                val result = repository.registerLeague(params)
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/league/createByName
            post("/createByName/{name}"){
                val name = call.parameters["name"] ?: ""
                val result = repository.registerLeagueByName(name)
                call.respond(result.statusCode, result)
            }
            // http://localhost:8080/league/update/{name}
            put("/update/{name}") {
                val name = call.parameters["name"] ?: ""
                val params = call.receive<CreateLeagueParams>()
                val result = repository.updateLeague(name, params)
                call.respond(result.statusCode, result)

            }
            // http://localhost:8080/league/dedelete/{name}
            delete ("/deleteByName/{name}"){
                val name = call.parameters["name"] ?: ""
                val result = repository.deleteLeague(name)
                call.respond(result.statusCode, result)
            }
        }
    }
}