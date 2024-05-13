package com.backtussam

import com.backtussam.db.DatabaseFactory
import com.backtussam.installs.configureCORS
import com.backtussam.installs.configureContentNegotiation
import com.backtussam.repositories.league.LeagueRepository
import com.backtussam.repositories.league.LeagueRepositoryImpl
import com.backtussam.repositories.player.PlayerRepository
import com.backtussam.repositories.player.PlayerRepositoryImpl
import com.backtussam.routes.authRoutes
import com.backtussam.routes.leagueRoutes
import com.backtussam.security.configureSecurity
import com.backtussam.services.league.LeagueService
import com.backtussam.services.league.LeagueServiceImpl
import com.backtussam.services.player.PlayerService
import com.backtussam.services.player.PlayerServiceImpl
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1"){
        myApplicationModule()

    }.start(wait = true)
}

fun Application.myApplicationModule() {
    DatabaseFactory.init()
    configureContentNegotiation()
    configureCORS()
    configureSecurity()

    val service: PlayerService = PlayerServiceImpl()
    val repository: PlayerRepository = PlayerRepositoryImpl(service)

    val serviceLeague: LeagueService = LeagueServiceImpl()
    val repositoryLeague: LeagueRepository = LeagueRepositoryImpl(serviceLeague)

    authRoutes(repository)

    leagueRoutes(repositoryLeague)

    
    routing {
        authenticate {
            get("/testUrl") {
                call.respondText("Hello World")
            }
        }
    }
}
