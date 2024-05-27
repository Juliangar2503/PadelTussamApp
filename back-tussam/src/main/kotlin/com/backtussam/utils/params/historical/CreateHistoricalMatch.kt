package com.backtussam.utils.params.historical

data class CreateHistoricalMatch (
    val id_player: Int,
    val id_match: Int,
    val score: String,
    val isWin: Boolean,
    val partner: String,
    val name_league: String
)