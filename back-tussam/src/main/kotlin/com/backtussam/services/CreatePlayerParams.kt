package com.backtussam.services

data class CreatePlayerParams(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val userName: String
)
