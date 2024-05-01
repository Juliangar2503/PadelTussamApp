package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object PlayerNotificationTable: Table("player_notifications"){
    val id = integer("id").autoIncrement()
    val id_player = integer("id_player").references(PlayerTable.id)
    val id_notification = integer("id_notification").references(NotificationTable.id)
    val read = bool("read")
    val date = varchar("date", 256)
    override val primaryKey = PrimaryKey(id)
}