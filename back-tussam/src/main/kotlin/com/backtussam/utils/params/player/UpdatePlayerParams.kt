package com.backtussam.utils.params.player

data class UpdatePlayerParams (
    val id: Int?,
    val name: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
    val location: String?,
    val nickname: String?,
    val avatar: String?,
    val createdAt: String?,
    val authToken: String?,
    val points: Int?,
    val active: Boolean?,
    val gameWon: Int?,
    val gameLost : Int?,
    val gameDifference: Int?,
    val gamePlayed: Int?,
    val leagueId: Int?,
    val roleId: Int?
)
