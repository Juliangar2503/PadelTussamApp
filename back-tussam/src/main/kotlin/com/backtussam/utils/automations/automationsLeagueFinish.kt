package com.backtussam.utils.automations

import com.backtussam.repositories.player.PlayerRepository
import com.backtussam.repositories.player.PlayerRepositoryImpl
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun Application.automationsLeagueFinish( repository: PlayerRepository){
    runBlocking {
        launch {
            while (true) {
                repository.checkAndFinishLeagues()
                Thread.sleep(1000 * 60 * 60 * 24)
            }
        }
    }
}