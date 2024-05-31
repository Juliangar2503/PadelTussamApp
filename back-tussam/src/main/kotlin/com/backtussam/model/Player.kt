package com.backtussam.model

import com.backtussam.db.tables.PlayerTable.default
import com.backtussam.db.tables.PlayerTable.integer

data class Player(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val location: String? = null,
    val nickname: String? = null,
    val avatar: String = "",
    var points: Int = 0,
    val createdAt: String,
    val active: Boolean = false,
    val gameWon: Int = 0,
    val gameLost : Int = 0,
    val gameDifference: Int = 0,
    val gamePlayed: Int = 0,
    val leagueId: Int? = null,
    val roleId: Int? = null,
    var authToken: String? = null
)
