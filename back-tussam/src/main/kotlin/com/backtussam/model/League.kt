package com.backtussam.model
data class League (
    var id: Int,
    var name: String,
    var duration: Int = 4,
    var ascent: Int = 6,
    var descent: Int = 6,
    var startDate: String

)
