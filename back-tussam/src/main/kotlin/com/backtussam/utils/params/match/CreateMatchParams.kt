package com.backtussam.utils.params.match

data class CreateMatchParams(
    val id_player1: Int? = null,
    val id_player2:  Int? = null,
    val id_player3:  Int? = null,
    val id_player4:  Int? = null,
    val scoreSet1:  Int? = null,
    val scoreSet2:  Int? = null,
    val scoreSet3:  Int? = null,
    val matchResult: String? = null,
    val date: String? = null,
    val open: Boolean = false,
    val chat:  Int? = null,
    val court:  Int? = null
)
