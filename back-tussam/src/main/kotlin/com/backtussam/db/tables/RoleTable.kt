package com.backtussam.db.tables

import org.jetbrains.exposed.sql.Table

object RoleTable : Table("roles"){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    override val primaryKey = PrimaryKey(id)
}