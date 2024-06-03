package com.backtussam.routes

import com.backtussam.repositories.court.CourtRepository
import com.backtussam.utils.params.court.CreateCourtParams
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.courtsRoutes(repository: CourtRepository){
    routing {
        authenticate {
            // http://localhost:8080/courts
            route("/courts") {
                // http://localhost:8080/courts/all
                get("/all") {
                    val result = repository.getCourts()
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/courts/findById/{id}
                get ("/findById/{id}"){
                    val id = call.parameters["id"]?.toIntOrNull() ?: 0
                    val result = repository.getCourtById(id)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/courts/find/{name}
                get("/findByName/{name}") {
                    val name = call.parameters["name"] ?: ""
                    val result = repository.getCourtByName(name)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/courts/create/{name}
                post("/create/{name}") {
                    val name = call.parameters["name"] ?: ""
                    val result = repository.createCourt(name)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/courts/update/{name}
                put("/update/{name}") {
                    val name = call.parameters["name"] ?: ""
                    val params = call.receive<CreateCourtParams>()
                    val result = repository.updateCourt(name, params)
                    call.respond(result.statusCode, result)
                }
                // http://localhost:8080/courts/delete/{name}
                delete("/delete/{name}") {
                    val name = call.parameters["name"] ?: ""
                    val result = repository.deleteCourt(name)
                    call.respond(result.statusCode, result)
                }
            }
        }
    }

}