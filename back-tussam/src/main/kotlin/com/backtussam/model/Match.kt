package com.backtussam.model

data class Match (
    val id: Int,
    val id_player1: Int? = null,
    val id_player2:  Int? = null,
    val id_player3:  Int? = null,
    val id_player4:  Int? = null,
    val scoreSet1A:  Int? = null,
    val scoreSet1B:  Int? = null,
    val scoreSet2A:  Int? = null,
    val scoreSet2B:  Int? = null,
    val scoreSet3A:  Int? = null,
    val scoreSet3B:  Int? = null,
    val matchResult: String? = null,
    val date: String? = null,
    val open: Boolean = false,
    val type: String? = null,
    val level: Int? = null,
    val confirmResult1: Boolean = false,
    val confirmResult2: Boolean = false,
    val chat:  Int? = null,
    val court:  Int? = null
)