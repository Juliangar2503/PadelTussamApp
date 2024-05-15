package com.backtussam.model

data class Player(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val location: String? = null,
    val nickname: String? = null,
    val avatar: String = "",
    val points: Int = 0,
    val createdAt: String,
    val leagueId: Int? = null,
    val roleId: Int? = null,
    var authToken: String? = null
)
