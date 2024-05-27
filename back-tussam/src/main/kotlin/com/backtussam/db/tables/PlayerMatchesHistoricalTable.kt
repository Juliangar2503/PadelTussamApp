package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime

object PlayerMatchesHistoricalTable: Table("player_matches_historical"){
    val id = integer("id").autoIncrement()
    val id_player = integer("id_player").references(PlayerTable.id)
    val id_match = integer("id_match").references(MatchTable.id)
    val score = varchar("score", 256).default("")
    val is_win = bool("is_win").default(false)
    val date = varchar("date", 256).default(LocalDateTime.now().toString())
    val partner = varchar("partner",256).default("")
    val name_league = varchar("name_league", 256).default("")
    override val primaryKey = PrimaryKey(id)
}