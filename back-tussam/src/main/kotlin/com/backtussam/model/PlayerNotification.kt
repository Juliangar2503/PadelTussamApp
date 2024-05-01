package com.backtussam.model

data class PlayerNotification(
val id: Int,
    val idPlayer: Int,
    val idNotification: Int,
    val read: Boolean? = false,
    val date: String
)
