package com.backtussam.utils.params.court

data class CreateCourtParams(
    val name: String,
    val address: String,
    val province: String,
    val latitude: Double,
    val longitude: Double
)
