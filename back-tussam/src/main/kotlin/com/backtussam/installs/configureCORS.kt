package com.backtussam.installs

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    install(CORS) {
        HttpMethod.Options
        HttpMethod.Put
        HttpMethod.Delete
        HttpMethod.Patch
        HttpHeaders.Authorization
        HttpHeaders.ContentType
        HttpHeaders.AccessControlAllowOrigin
        HttpHeaders.AccessControlAllowHeaders
        HttpHeaders.AccessControlAllowMethods
        anyHost()
    }
}

