package com.backtussam.utils

import com.backtussam.db.tables.RoleTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

object SeedData {
    fun seedRoles() {
        val roles = listOf("ADMIN", "BASIC")

        roles.forEachIndexed { index, role ->
            if (RoleTable.select { RoleTable.name eq role }.empty()) {
                RoleTable.insert {
                    it[id] = index + 1
                    it[name] = role
                }
            }
        }
    }
}