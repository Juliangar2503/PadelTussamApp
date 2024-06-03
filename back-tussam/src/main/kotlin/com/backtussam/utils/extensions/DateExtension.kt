package com.backtussam.utils.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

fun LocalDateTime.toReadableFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm", Locale("es"))
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime {
    val formats = listOf(
        DateTimeFormatter.ISO_DATE_TIME,
        DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm", Locale("es"))
    )

    for (format in formats) {
        try {
            return LocalDateTime.parse(this, format)
        } catch (e: DateTimeParseException) {
            // Ignorar y probar el siguiente formato
        }
    }

    throw DateTimeParseException("No se pudo parsear la fecha: $this", this, 0)
}
