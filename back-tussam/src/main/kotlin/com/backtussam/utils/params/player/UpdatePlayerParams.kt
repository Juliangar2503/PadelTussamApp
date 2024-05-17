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
    val points: Int?,
    val leagueId: Int?,
    val roleId: Int?
)