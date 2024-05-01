package com.backtussam.model

data class PlayerMatchesHistorical(
    val id: Int,
    val id_player: Int,
    val id_match: Int,
    val score: Int,
    val date: String,
    val partner: Int
)
