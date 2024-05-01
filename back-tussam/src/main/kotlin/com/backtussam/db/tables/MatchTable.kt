package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object MatchTable : Table("matches"){
    val id = integer("id").autoIncrement()
    val id_player1 = integer("id_player1").references(PlayerTable.id)
    val id_player2 = integer("id_player2").references(PlayerTable.id)
    val id_player3 = integer("id_player3").references(PlayerTable.id)
    val id_player4 = integer("id_player4").references(PlayerTable.id)
    val scoreSet1 = integer("scoreSet1")
    val scoreSet2 = integer("scoreSet2")
    val scoreSet3 = integer("scoreSet3")
    val matchResult = varchar("matchResult", 256)
    val date = varchar("date", 256)
    val open = bool("open")
    val chat = integer("chat").references(ChatTable.id)
    override val primaryKey = PrimaryKey(id)
}