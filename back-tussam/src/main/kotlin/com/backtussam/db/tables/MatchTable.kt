package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object MatchTable : Table("matches"){
    val id = integer("id").autoIncrement()
    val id_player1 = integer("id_player1").references(PlayerTable.id).nullable()
    val id_player2 = integer("id_player2").references(PlayerTable.id).nullable()
    val id_player3 = integer("id_player3").references(PlayerTable.id).nullable()
    val id_player4 = integer("id_player4").references(PlayerTable.id).nullable()
    val scoreSet1A = integer("scoreSet1A").nullable().default(0)
    val scoreSet1B = integer("scoreSet1B").nullable().default(0)
    val scoreSet2A = integer("scoreSet2A").nullable().default(0)
    val scoreSet2B = integer("scoreSet2B").nullable().default(0)
    val scoreSet3A = integer("scoreSet3A").nullable().default(0)
    val scoreSet3B = integer("scoreSet3B").nullable().default(0)
    val matchResult = varchar("matchResult", 256).nullable()
    val type = varchar("type", 256).nullable()
    val level = integer("level").nullable()
    val date = varchar("date", 256).default("")
    val open = bool("open").default(true)
    val confirmResult1 = bool("confirmResult1").default(false)
    val confirmResult2 = bool("confirmResult2").default(false)
    val chat = integer("chat").references(ChatTable.id).nullable()
    val court = integer("court").references(CourtTable.id).nullable()
    override val primaryKey = PrimaryKey(id)
}