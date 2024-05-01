package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object PlayerTable: Table("players") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val lastName = varchar("last_name", 256)
    val email = varchar("email", 256)
    val password = text("password")
    val userName = varchar("user_name", 256)
    val avatar = text("avatar")
    val points = integer("points")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val leagueId = integer("league_id").references(LeagueTable.id)
    val roleId = integer("role_id").references(RoleTable.id)
    override val primaryKey = PrimaryKey(id)
}