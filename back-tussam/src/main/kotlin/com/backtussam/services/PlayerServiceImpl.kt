package com.backtussam.services

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.db.tables.PlayerTable
import com.backtussam.model.Player
import com.backtussam.security.hash
import com.backtussam.utils.params.CreatePlayerParams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement


class PlayerServiceImpl : PlayerService {

    //Register player
    override suspend fun registerPlayer(params: CreatePlayerParams): Player? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = PlayerTable.insert {
                it[email] = params.email
                it[password] = hash(params.password)
                it[name] = params.name
                it[lastName] = params.lastName
                it[userName] = params.userName
            }
        }
        return rowToPlayer(statement?.resultedValues?.get(0))
    }

    //Find player by email
    override suspend fun findPlayerByEmail(email: String): Player? {
        val player = dbQuery {
            PlayerTable.select{ PlayerTable.email.eq(email) }
                .map { rowToPlayer(it) }.singleOrNull()
        }
        return player
    }

    //Find pswd by email
    override suspend fun findPasswordByEmail(email: String): String? {
        val password = dbQuery {
            PlayerTable.select{ PlayerTable.email.eq(email) }
                .map { it[PlayerTable.password] }.singleOrNull()
        }
        return password
    }

    //Función utilizada para mapear los datos de la base de datos a un objeto Player
    private fun rowToPlayer(row: ResultRow?): Player? {
        return if (row == null) null
        else Player(
            id = row[PlayerTable.id],
            name = row[PlayerTable.name],
            lastName = row[PlayerTable.lastName],
            email = row[PlayerTable.email],
            createdAt = row[PlayerTable.createdAt].toString(),
            userName = row[PlayerTable.userName],
        )
    }
}