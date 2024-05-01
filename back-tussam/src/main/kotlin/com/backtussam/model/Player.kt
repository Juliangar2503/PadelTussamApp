package com.backtussam.model

data class Player(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val userName: String,
    val avatar: String,
    val createdAt: String,
    val leagueId: Int,
    val roleId: Int,
    var authToken: String? = null
)
