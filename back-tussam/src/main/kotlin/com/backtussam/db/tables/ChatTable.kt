package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object ChatTable : Table("chats"){
    val id = integer("id").autoIncrement()
    val message = text("message")
    val date = varchar("date", 256)
    override val primaryKey = PrimaryKey(id)
}