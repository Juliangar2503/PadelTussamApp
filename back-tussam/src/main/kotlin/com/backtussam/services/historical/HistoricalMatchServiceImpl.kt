package com.backtussam.services.historical


import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.db.tables.PlayerMatchesHistoricalTable
import com.backtussam.model.PlayerMatchesHistorical
import com.backtussam.utils.params.historical.CreateHistoricalMatch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class HistoricalMatchServiceImpl : HistoricalMatchService {
    override suspend fun getHistoricalMatch(id: Int): PlayerMatchesHistorical? {
        return dbQuery{
            PlayerMatchesHistoricalTable.select {
                PlayerMatchesHistoricalTable.id eq id
            }.mapNotNull { rowToPlayerMatchesHistorical(it) }
                .singleOrNull()
        }
    }

    override suspend fun getHistoricalMatches(): List<PlayerMatchesHistorical> {
        return dbQuery{
            PlayerMatchesHistoricalTable.selectAll().mapNotNull { rowToPlayerMatchesHistorical(it) }
        }
    }

    override suspend fun getHistoricalMatchesByPlayer(id: Int): List<PlayerMatchesHistorical> {
        return dbQuery{
            PlayerMatchesHistoricalTable.select {
                PlayerMatchesHistoricalTable.id_player eq id
            }.mapNotNull { rowToPlayerMatchesHistorical(it) }
        }
    }

    override suspend fun registerHistoricalMatch(params: CreateHistoricalMatch): PlayerMatchesHistorical? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = PlayerMatchesHistoricalTable.insert {
                it[PlayerMatchesHistoricalTable.id_player] = params.id_player
                it[PlayerMatchesHistoricalTable.id_match] = params.id_match
                it[PlayerMatchesHistoricalTable.score] = params.score
                it[PlayerMatchesHistoricalTable.partner] = params.partner
                it[PlayerMatchesHistoricalTable.name_league] = params.name_league
                it[PlayerMatchesHistoricalTable.is_win] = params.isWin
            }
        }
        return getHistoricalMatch(statement?.resultedValues?.get(0)?.get(PlayerMatchesHistoricalTable.id) as Int)
    }

    override suspend fun updateHistoricalMatch(id: Int, params: CreateHistoricalMatch): PlayerMatchesHistorical? {
        dbQuery {
            PlayerMatchesHistoricalTable.update({PlayerMatchesHistoricalTable.id eq id}){
                it[PlayerMatchesHistoricalTable.id_player] = params.id_player
                it[PlayerMatchesHistoricalTable.id_match] = params.id_match
                it[PlayerMatchesHistoricalTable.score] = params.score
                it[PlayerMatchesHistoricalTable.partner] = params.partner
                it[PlayerMatchesHistoricalTable.name_league] = params.name_league
                it[PlayerMatchesHistoricalTable.is_win] = params.isWin
            }
        }
        return getHistoricalMatch(id)
    }

    override suspend fun deleteHistoricalMatch(id: Int): Boolean {
        return dbQuery {
            PlayerMatchesHistoricalTable.deleteWhere { PlayerMatchesHistoricalTable.id eq id } > 0
        }
    }

    //Función utilizada para mapear los datos de la base de datos a un objeto
    private fun rowToPlayerMatchesHistorical(row: ResultRow?): PlayerMatchesHistorical? {
        return if (row == null) null
        else PlayerMatchesHistorical(
            id = row[PlayerMatchesHistoricalTable.id],
            id_player = row[PlayerMatchesHistoricalTable.id_player],
            id_match = row[PlayerMatchesHistoricalTable.id_match],
            score = row[PlayerMatchesHistoricalTable.score],
            isWin = row[PlayerMatchesHistoricalTable.is_win],
            date = row[PlayerMatchesHistoricalTable.date],
            partner = row[PlayerMatchesHistoricalTable.partner],
            name_league = row[PlayerMatchesHistoricalTable.name_league]
        )
    }

}