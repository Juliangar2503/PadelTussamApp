package com.backtussam.model

import com.backtussam.db.tables.CourtTable

data class Court(
    val id:Int,
    val name:String,
    val address:String,
    val latitude:Double? = null,
    val longitude:Double? = null

)