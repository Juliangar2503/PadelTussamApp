package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object CourtTable: Table("courts"){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val address = varchar("address", 256)
    val latitude = double("latitude")
    val longitude = double("longitude")
    override val primaryKey = PrimaryKey(id)
}