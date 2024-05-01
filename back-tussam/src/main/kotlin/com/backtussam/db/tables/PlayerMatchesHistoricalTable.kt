package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object PlayerMatchesHistoricalTable: Table("player_matches_historical"){

    val id = integer("id").autoIncrement()
    val id_player = integer("id_player").references(PlayerTable.id)
    val id_match = integer("id_match").references(MatchTable.id)
    val score = integer("score")
    val date = varchar("date", 256)
    val partner = integer("partner").references(PlayerTable.id)
    override val primaryKey = PrimaryKey(id)
}