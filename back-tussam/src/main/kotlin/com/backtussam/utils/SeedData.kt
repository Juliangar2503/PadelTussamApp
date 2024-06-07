package com.backtussam.utils

import com.backtussam.db.tables.PlayerTable
import com.backtussam.db.tables.RoleTable
import com.backtussam.security.hash
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

object SeedData {
    fun init() {
        seedRoles()
        seedPlayers()
    }

    private fun seedRoles() {
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

    private fun seedPlayers() {
       val players = listOf(
           "Julian",
           "Andrea",
       )
        players.forEachIndexed { index, player ->
            if (PlayerTable.select { PlayerTable.name eq player }.empty()) {
                PlayerTable.insert {
                    it[id] = index + 1
                    it[name] = player
                    it[lastName] = "Garrido"
                    it[password] = hash("1234")
                    it[email] = "$player@gmail.com"
                    it[roleId] = 1
                }
            }
        }
    }
}