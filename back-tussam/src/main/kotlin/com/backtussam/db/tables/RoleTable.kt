package com.backtussam.db.tables

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

object RoleTable : Table("roles"){
    val id = integer("id")
    val name = varchar("name", 256)
    override val primaryKey = PrimaryKey(id)
}