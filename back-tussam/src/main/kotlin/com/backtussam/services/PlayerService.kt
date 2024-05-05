package com.backtussam.services

import com.backtussam.model.Player

interface PlayerService {
    suspend fun registerPlayer(params: CreatePlayerParams): Player?
    suspend fun findPlayerByEmail(email: String): Player?
    suspend fun findPasswordByEmail(email: String): String?
}