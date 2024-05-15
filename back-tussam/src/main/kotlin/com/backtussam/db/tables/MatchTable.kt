package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object MatchTable : Table("matches"){
    val id = integer("id").autoIncrement()
    val id_player1 = integer("id_player1").references(PlayerTable.id).nullable()
    val id_player2 = integer("id_player2").references(PlayerTable.id).nullable()
    val id_player3 = integer("id_player3").references(PlayerTable.id).nullable()
    val id_player4 = integer("id_player4").references(PlayerTable.id).nullable()
    val scoreSet1 = integer("scoreSet1").nullable()
    val scoreSet2 = integer("scoreSet2").nullable()
    val scoreSet3 = integer("scoreSet3").nullable()
    val matchResult = varchar("matchResult", 256).nullable()
    val date = varchar("date", 256).default("")
    val open = bool("open").default(false)
    val chat = integer("chat").references(ChatTable.id).nullable()
    val court = integer("court").references(CourtTable.id).nullable()
    override val primaryKey = PrimaryKey(id)
}