package com.backtussam.db.tables

import com.backtussam.utils.Variable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object PlayerTable: Table("players") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val lastName = varchar("last_name", 256)
    val email = varchar("email", 256)
    val password = text("password")
    val location = varchar("location", 256).nullable()
    val nickname = varchar("nikename", 256).nullable()
    val avatar = text("avatar").default(Variable.defaultAvatar)
    val points = integer("points").default(0)
    val active = bool("active").default(false)
    val gameWon = integer("game_won").default(0)
    val gameLost = integer("game_lost").default(0)
    val gameDifference = integer("game_difference").default(0)
    val gamePlayed = integer("game_played").default(0)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val leagueId = integer("league_id").references(LeagueTable.id).nullable()
    val roleId = integer("role_id").references(RoleTable.id).nullable()
    override val primaryKey = PrimaryKey(id)
}