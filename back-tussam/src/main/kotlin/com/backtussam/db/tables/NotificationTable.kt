package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object NotificationTable : Table("notifications"){
    val id = integer("id").autoIncrement()
    val title = varchar("title", 256)
    val message = text("message")
    override val primaryKey = PrimaryKey(id)
}