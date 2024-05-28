package com.backtussam.utils.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDateTime.toReadableFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm", Locale("es"))
    return this.format(formatter)
}