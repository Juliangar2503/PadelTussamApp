package com.backtussam.services.player

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.db.tables.PlayerTable
import com.backtussam.model.League
import com.backtussam.model.Player
import com.backtussam.security.hash
import com.backtussam.utils.extensions.toReadableFormat
import com.backtussam.utils.params.player.CreatePlayerParams
import com.backtussam.utils.params.player.UpdatePlayerParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.statements.InsertStatement


class PlayerServiceImpl : PlayerService {
    /*** OBTENER JUGADORES ***/
    override suspend fun getPlayers(): List<Player> {
        return dbQuery {
            PlayerTable.selectAll().mapNotNull { rowToPlayer(it) }
        }
    }

    override suspend fun getPlayersByLeague(leagueId: Int): List<Player> {
        return dbQuery {
            PlayerTable.select { PlayerTable.leagueId eq leagueId }
                .orderBy(PlayerTable.points to SortOrder.DESC, PlayerTable.gameDifference to SortOrder.DESC)
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

    /*** REGISTRAR JUGADOR ***/
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

    override suspend fun changePassword(email: String, password: String): Boolean {
        return dbQuery {
            PlayerTable.update({ PlayerTable.email eq email }) {
                it[PlayerTable.password] = hash(password)
            } > 0
        }
    }

    /*** ACTUALIZAR Y ELIMINAR JUGADOR ***/
    override suspend fun updatePlayer(email: String, params: UpdatePlayerParams): Player? {
        dbQuery {
            PlayerTable.update({ PlayerTable.email eq email }) {
                if (params.name != null) it[name] = params.name
                if (params.lastName != null) it[lastName] = params.lastName
                if (params.email != null) it[PlayerTable.email] = params.email
                if (params.password != null) it[password] = hash(params.password)
                if (params.location != null) it[PlayerTable.location] = params.location
                if (params.nickname != null) it[PlayerTable.nickname] = params.nickname
                if (params.avatar != null) it[PlayerTable.avatar] = params.avatar
                if (params.points != null) it[PlayerTable.points] = params.points
                if (params.active != null) it[PlayerTable.active] = params.active
                if (params.leagueId != null) {
                    it[PlayerTable.leagueId] = params.leagueId
                    it[PlayerTable.points] = 0
                    it[PlayerTable.gameWon] = 0
                    it[PlayerTable.gameLost] = 0
                    it[PlayerTable.gameDifference] = 0
                    it[PlayerTable.gamePlayed] = 0
                }
                if (params.roleId != null) it[PlayerTable.roleId] = params.roleId
            }
        }
        return params.email?.let { findPlayerByEmail(it) }
    }

    override suspend fun deletePlayer(id: Int): Boolean {
        return dbQuery {
            PlayerTable.deleteWhere { PlayerTable.id eq id } > 0
        }
    }

    override suspend fun earnPoints(playerId: Int,point: Int): Boolean {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.points] = point
            } > 0
        }
    }

    override suspend fun changeLeague(playerId: Int, leagueId: League?): Boolean {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.leagueId] = leagueId?.id
            } > 0
        }
    }

    override suspend fun resetsPlayers(playerId: Int): Player? {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.points] = 0
                it[PlayerTable.gameWon] = 0
                it[PlayerTable.gameLost] = 0
                it[PlayerTable.gameDifference] = 0
                it[PlayerTable.gamePlayed] = 0
            } > 0
            PlayerTable.select { PlayerTable.id eq playerId }
                .mapNotNull { rowToPlayer(it) }
                .singleOrNull()
        }
    }

    /*** GANAR Y PERDER JUEGOS ***/
    override suspend fun winGames(playerId: Int, quantity: Int): Player? {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.gameWon] = PlayerTable.gameWon + quantity
                it[PlayerTable.gamePlayed] = PlayerTable.gamePlayed + 1
            }
            PlayerTable.select { PlayerTable.id eq playerId }
                .mapNotNull { rowToPlayer(it) }
                .singleOrNull()
        }
    }

    override suspend fun loseGames(playerId: Int, quantity: Int): Player? {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.gameLost] = PlayerTable.gameLost + quantity
            }
            PlayerTable.select { PlayerTable.id eq playerId }
                .mapNotNull { rowToPlayer(it) }
                .singleOrNull()
        }
    }

    override suspend fun differenceGames(playerId: Int, quantity: Int): Player? {
        return dbQuery {
            PlayerTable.update({ PlayerTable.id eq playerId }) {
                it[PlayerTable.gameDifference] = PlayerTable.gameDifference + quantity
            }
            PlayerTable.select { PlayerTable.id eq playerId }
                .mapNotNull { rowToPlayer(it) }
                .singleOrNull()
        }
    }


    /*** BUSCAR JUGADORES POR EMAIL Y PASSWORD ***/
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

    private fun rowToPlayer(row: ResultRow?): Player? {
        return if (row == null) null
        else Player(
            id = row[PlayerTable.id],
            name = row[PlayerTable.name],
            lastName = row[PlayerTable.lastName],
            email = row[PlayerTable.email],
            createdAt = row[PlayerTable.createdAt].toReadableFormat(),
            location = row[PlayerTable.location],
            nickname = row[PlayerTable.nickname],
            avatar = row[PlayerTable.avatar].toString(),
            points = row[PlayerTable.points],
            active = row[PlayerTable.active],
            gameWon = row[PlayerTable.gameWon],
            gameLost = row[PlayerTable.gameLost],
            gameDifference = row[PlayerTable.gameDifference],
            gamePlayed = row[PlayerTable.gamePlayed],
            leagueId = row[PlayerTable.leagueId],
            roleId = row[PlayerTable.roleId]
        )
    }
}