package com.backtussam.utils.params.court

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateCourtParams(
    val name: String? = null,
    val address: String ?= null,
)
