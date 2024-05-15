package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object LeagueTable: Table("leagues"){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val duration = integer("duration").default(4)
    val ascent = integer("ascent").default(6)
    val descent = integer("descent").default(6)
    val startDate = datetime("created_at").clientDefault { LocalDateTime.now() }
    val endDate = datetime("end_date").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}