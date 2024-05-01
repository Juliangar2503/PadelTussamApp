package com.backtussam.model

data class Match (
    val id: Int,
    val id_player1: Int,
    val id_player2: Int,
    val id_player3: Int,
    val id_player4: Int,
    val scoreSet1: Int,
    val scoreSet2: Int,
    val scoreSet3: Int,
    val matchResult: String,
    val date: String,
    val open: Boolean,
    val chat: Int
)