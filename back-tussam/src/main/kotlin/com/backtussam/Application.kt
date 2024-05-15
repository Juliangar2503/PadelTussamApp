package com.backtussam

import com.backtussam.db.DatabaseFactory
import com.backtussam.installs.configureCORS
import com.backtussam.installs.configureContentNegotiation
import com.backtussam.repositories.court.CourtRepository
import com.backtussam.repositories.court.CourtRepositoryImpl
import com.backtussam.repositories.league.LeagueRepository
import com.backtussam.repositories.league.LeagueRepositoryImpl
import com.backtussam.repositories.match.MatchRepository
import com.backtussam.repositories.match.MatchRepositoryImpl
import com.backtussam.repositories.player.PlayerRepository
import com.backtussam.repositories.player.PlayerRepositoryImpl
import com.backtussam.routes.authRoutes
import com.backtussam.routes.courtsRoutes
import com.backtussam.routes.leagueRoutes
import com.backtussam.routes.matchesRoutes
import com.backtussam.security.configureSecurity
import com.backtussam.services.court.CourtService
import com.backtussam.services.court.CourtServiceImpl
import com.backtussam.services.league.LeagueService
import com.backtussam.services.league.LeagueServiceImpl
import com.backtussam.services.match.MatchService
import com.backtussam.services.match.MatchServiceImpl
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



    val serviceLeague: LeagueService = LeagueServiceImpl()
    val repositoryLeague: LeagueRepository = LeagueRepositoryImpl(serviceLeague)

    val servicePlayer: PlayerService = PlayerServiceImpl()
    val repositoryPlayer: PlayerRepository = PlayerRepositoryImpl(servicePlayer, serviceLeague)

    val serviceCourt: CourtService = CourtServiceImpl()
    val repositoryCourt: CourtRepository = CourtRepositoryImpl(serviceCourt)

    val serviceMatch: MatchService = MatchServiceImpl()
    val repositoryMatch: MatchRepository = MatchRepositoryImpl(serviceMatch)

    authRoutes(repositoryPlayer)

    leagueRoutes(repositoryLeague)

    courtsRoutes(repositoryCourt)

    matchesRoutes(repositoryMatch)

    
    routing {
        authenticate {
            get("/testUrl") {
                call.respondText("Hello World")
            }
        }
    }
}
