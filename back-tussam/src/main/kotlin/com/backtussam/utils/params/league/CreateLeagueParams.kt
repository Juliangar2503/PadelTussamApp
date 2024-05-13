package com.backtussam.utils.params.league

data class CreateLeagueParams(
    val name: String,
    val duration: Int,
    val ascent: Int,
    val descent: Int,
    val startDate: String
)