package com.backtussam.model

data class PlayerMatchesHistorical(
    val id: Int,
    val id_player: Int,
    val id_match: Int,
    val score: String,
    val isWin: Boolean,
    val date: String,
    val partner: String,
    val name_league: String
)
