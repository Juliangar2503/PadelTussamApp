package com.backtussam.services.player

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.db.tables.PlayerTable
import com.backtussam.model.Player
import com.backtussam.security.hash
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.UpdatePlayerParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement


class PlayerServiceImpl : PlayerService {
    override suspend fun getPlayers(): List<Player> {
        return dbQuery {
            PlayerTable.selectAll().mapNotNull { rowToPlayer(it) }
        }
    }

    //Comprobar en el repositorio si la liga está registrada
    override suspend fun getPlayersByLeague(leagueId: Int): List<Player> {
        return dbQuery {
            PlayerTable.select { PlayerTable.leagueId eq leagueId }
                .mapNotNull { rowToPlayer(it) }
        }
    }

    override suspend fun getPlayersByName(name: String): List<Player> {
        return dbQuery {
            PlayerTable.select { PlayerTable.name eq name }
                .mapNotNull { rowToPlayer(it) }
        }
    }

    override suspend fun getPlayerById(id: Int): Player? {
        return dbQuery {
            PlayerTable.select { PlayerTable.id eq id }
                .mapNotNull { rowToPlayer(it) }
                .singleOrNull()
        }
    }

    override suspend fun registerPlayer(params: CreatePlayerParams): Player? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = PlayerTable.insert {
                it[email] = params.email
                it[password] = hash(params.password)
                it[name] = params.name
                it[lastName] = params.lastName
            }
        }
        return rowToPlayer(statement?.resultedValues?.get(0))
    }

    override suspend fun addPlayerToLeague(playerId: Int, leagueId: Int): Boolean {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.leagueId] = leagueId
            } > 0
        }
    }

    override suspend fun findPlayerByEmail(email: String): Player? {
        val player = dbQuery {
            PlayerTable.select{ PlayerTable.email.eq(email) }
                .map { rowToPlayer(it) }.singleOrNull()
        }
        return player
    }

    override suspend fun findPasswordByEmail(email: String): String? {
        val password = dbQuery {
            PlayerTable.select{ PlayerTable.email.eq(email) }
                .map { it[PlayerTable.password] }.singleOrNull()
        }
        return password
    }

    override suspend fun updatePlayer(email: String, params: UpdatePlayerParams): Player? {
        dbQuery {
            PlayerTable.update({ PlayerTable.email eq email }) {
                it[name] = params.name
                it[lastName] = params.lastName
                it[PlayerTable.email] = params.email
                it[password] = hash(params.password)
                it[PlayerTable.location] = params.location
                it[PlayerTable.nickname] = params.nickname
                it[PlayerTable.avatar] = params.avatar
                it[PlayerTable.points] = params.points
                it[PlayerTable.leagueId] = params.leagueId
                it[PlayerTable.roleId] = params.roleId
            }
        }
        return findPlayerByEmail(params.email)
    }

    override suspend fun deletePlayer(id: Int): Boolean {
        return dbQuery {
            PlayerTable.deleteWhere { PlayerTable.id eq id } > 0
        }
    }

    private fun rowToPlayer(row: ResultRow?): Player? {
        return if (row == null) null
        else Player(
            id = row[PlayerTable.id],
            name = row[PlayerTable.name],
            lastName = row[PlayerTable.lastName],
            email = row[PlayerTable.email],
            createdAt = row[PlayerTable.createdAt].toString(),
            location = row[PlayerTable.location],
            nickname = row[PlayerTable.nickname],
            avatar = row[PlayerTable.avatar].toString(),
            points = row[PlayerTable.points],
            leagueId = row[PlayerTable.leagueId],
            roleId = row[PlayerTable.roleId]
        )
    }
}