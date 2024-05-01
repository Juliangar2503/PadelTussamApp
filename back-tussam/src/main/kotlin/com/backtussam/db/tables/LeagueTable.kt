package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object LeagueTable: Table("leagues"){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val duration = integer("duration")
    val ascent = integer("ascent")
    val descent = integer("descent")
    val startDate = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}