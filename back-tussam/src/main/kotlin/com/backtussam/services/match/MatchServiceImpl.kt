package com.backtussam.services.match

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.model.Match
import com.backtussam.db.tables.MatchTable
import com.backtussam.utils.params.match.CreateMatchParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class MatchServiceImpl : MatchService {
    override suspend fun getMatches(): List<Match> {
        return dbQuery {
            MatchTable.selectAll().mapNotNull { rowToMatch(it) }
        }
    }

    override suspend fun getMatchById(id: Int): Match? {
        return dbQuery {
            MatchTable.select {
                MatchTable.id eq id
            }.mapNotNull { rowToMatch(it) }
                .singleOrNull()
        }
    }

    override suspend fun createMatch(params: CreateMatchParams): Match? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = MatchTable.insert { it.setMatchParams(params) }
        }
        return rowToMatch(statement?.resultedValues?.get(0))
    }

    override suspend fun updateMatch(id: Int, params: CreateMatchParams): Match? {
        dbQuery {
            MatchTable.update({ MatchTable.id eq id }) { it.setMatchParams(params) }
        }
        return getMatchById(id)
    }

    override suspend fun deleteMatch(id: Int): Boolean {
        return dbQuery {
            MatchTable.deleteWhere { MatchTable.id eq MatchTable.id } > 0
        }
    }

    //Utilizar esta función ya que se repiten lo mismo en los metodos de update y create
    private fun UpdateBuilder<*>.setMatchParams(params: CreateMatchParams) {
        this[MatchTable.id_player1] = params.id_player1!!
        this[MatchTable.id_player2] = params.id_player2!!
        this[MatchTable.id_player3] = params.id_player3!!
        this[MatchTable.id_player4] = params.id_player4!!
        this[MatchTable.scoreSet1] = params.scoreSet1
        this[MatchTable.scoreSet2] = params.scoreSet2
        this[MatchTable.scoreSet3] = params.scoreSet3
        this[MatchTable.matchResult] = params.matchResult
        this[MatchTable.date] = params.date ?: ""
        this[MatchTable.open] = params.open
        this[MatchTable.chat] = params.chat
        this[MatchTable.court] = params.court ?: 2
    }

    private fun rowToMatch(row: ResultRow?): Match? {
        return if (row == null) null
        else Match(
            id = row[MatchTable.id],
            id_player1 = row[MatchTable.id_player1],
            id_player2 = row[MatchTable.id_player2],
            id_player3 = row[MatchTable.id_player3],
            id_player4 = row[MatchTable.id_player4],
            scoreSet1 = row[MatchTable.scoreSet1],
            scoreSet2 = row[MatchTable.scoreSet2],
            scoreSet3 = row[MatchTable.scoreSet3],
            matchResult = row[MatchTable.matchResult],
            date = row[MatchTable.date].toString(),
            open = row[MatchTable.open],
            chat = row[MatchTable.chat],
            court = row[MatchTable.court]
        )
    }
}