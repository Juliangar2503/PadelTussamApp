package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object PlayerLeagueHistoricalTable: Table("player_league_historical"){
    val id = integer("id").autoIncrement()
    val id_player = integer("id_player").references(PlayerTable.id)
    val id_league = integer("id_league").references(LeagueTable.id)
    val numberMatches = integer("number_matches").default(0)
    val points = integer("points")
    val positionLeague = integer("position_league")
    val date = varchar("date", 256)
    override val primaryKey = PrimaryKey(id)
}