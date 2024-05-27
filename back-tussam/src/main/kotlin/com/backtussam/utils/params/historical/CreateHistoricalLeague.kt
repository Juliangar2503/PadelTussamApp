package com.backtussam.utils.params.historical

data class CreateHistoricalLeague(
    val id_player:Int,
    val id_league:Int,
    val numberMatches:Int,
    val points:Int,
    val positionLeague:Int,
    val date:String
)