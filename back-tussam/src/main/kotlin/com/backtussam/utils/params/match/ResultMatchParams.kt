package com.backtussam.utils.params.match

data class ResultMatchParams(
    val scoreSet1A:  Int,
    val scoreSet1B:  Int,
    val scoreSet2A:  Int,
    val scoreSet2B:  Int,
    val scoreSet3A:  Int? = null,
    val scoreSet3B:  Int? = null,
    val open: Boolean = false
)

