package com.backtussam.db.tables

import com.backtussam.db.tables.LeagueTable.clientDefault
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object PlayerLeagueHistoricalTable: Table("player_league_historical"){
    val id = integer("id").autoIncrement()
    val id_player = integer("id_player").references(PlayerTable.id)
    val id_league = integer("id_league").references(LeagueTable.id)
    val numberMatches = integer("number_matches").default(0)
    val points = integer("points")
    val positionLeague = integer("position_league")
    val endDate = datetime("end_date").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id)
}