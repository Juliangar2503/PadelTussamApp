package com.backtussam.services.match

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.model.Match
import com.backtussam.db.tables.MatchTable
import com.backtussam.utils.extensions.toLocalDateTime
import com.backtussam.utils.extensions.toReadableFormat
import com.backtussam.utils.params.match.CreateMatchParams
import com.backtussam.utils.params.match.ResultMatchParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class MatchServiceImpl : MatchService {

    /****** OBTENER PARTIDOS ******/

    override suspend fun getMatches(): List<Match> {
        val query  =  dbQuery {
            MatchTable.selectAll().mapNotNull { rowToMatch(it) }
        }
        println("MatchService -> getMatches -> matches: $query")
        return query
    }

    override suspend fun getMatchById(id: Int): Match? {
        return dbQuery {
            MatchTable.select {
                MatchTable.id eq id
            }.mapNotNull { rowToMatch(it) }
                .singleOrNull()
        }
    }

    override suspend fun getMatchesByLeague(leagueId: Int): List<Match?> {
        return dbQuery {
            MatchTable.select {
                (MatchTable.level eq leagueId) and
                        (MatchTable.type eq "Competitive") and
                        (MatchTable.open eq true)
            }.mapNotNull { rowToMatch(it) }
        }
    }

    override suspend fun getMatchesByType(typoMatch: String): List<Match?> {
        return dbQuery {
            MatchTable.select {
                MatchTable.type eq typoMatch
            }.mapNotNull { rowToMatch(it) }
        }
    }

    override suspend fun getMatchesOpenByPlayer(playerId: Int): List<Match?> {
        return dbQuery {
            MatchTable.select {
                ((MatchTable.id_player1 eq playerId) or
                        (MatchTable.id_player2 eq playerId) or
                        (MatchTable.id_player3 eq playerId) or
                        (MatchTable.id_player4 eq playerId)) and
                        ((MatchTable.confirmResult1 eq false) or (MatchTable.confirmResult2 eq false))

            }.mapNotNull { rowToMatch(it) }
        }
    }

    override suspend fun getMatchesCloseByPlayer(playerId: Int): List<Match?> {
        return dbQuery {
            MatchTable.select {
                ((MatchTable.id_player1 eq playerId) or
                        (MatchTable.id_player2 eq playerId) or
                        (MatchTable.id_player3 eq playerId) or
                        (MatchTable.id_player4 eq playerId)) and
                        ((MatchTable.confirmResult1 eq true) and
                                (MatchTable.confirmResult2 eq true))

            }.mapNotNull { rowToMatch(it) }
        }
    }

    /***** CREAR, ACTUALIZAR Y ELIMINAR PARTIDOS ******/

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

    /**** OPERACIONES CON JUGADORES EN PARTIDOS *****/

    override suspend fun isPlayerInMatch(playerId: Int, matchId: Int): Boolean {
        return dbQuery {
            MatchTable.select {
                (MatchTable.id_player1 eq playerId) or
                        (MatchTable.id_player2 eq playerId) or
                        (MatchTable.id_player3 eq playerId) or
                        (MatchTable.id_player4 eq playerId)
            }.singleOrNull() != null
        }
    }

    override suspend fun getIdPlayersInMatch(matchId: Int): List<Int> {
        return dbQuery {
            MatchTable.select {
                MatchTable.id eq matchId
            }.mapNotNull {
                listOfNotNull(
                    it[MatchTable.id_player1],
                    it[MatchTable.id_player2],
                    it[MatchTable.id_player3],
                    it[MatchTable.id_player4]
                )
            }.flatten()
        }
    }

    override suspend fun addPlayerInMatch(idPlayer: Int, idMatch: Int, position: Int) : Match?{
        dbQuery {
            when(position){
                1 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player1] = idPlayer
                }
                2 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player2] = idPlayer
                }
                3 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player3] = idPlayer
                }
                4 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player4] = idPlayer
                }
                else -> {
                    println("Error al agregar jugador a partido")
                }
            }
        }
        return getMatchById(idMatch)
    }

    override suspend fun removePlayerInMatch(idPlayer: Int, idMatch: Int, position: Int): Match? {
    dbQuery {
            when(position){
                1 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player1] = null
                }
                2 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player2] = null
                }
                3 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player3] = null
                }
                4 -> MatchTable.update({ MatchTable.id eq idMatch }) {
                    it[MatchTable.id_player4] = null
                }
                else -> {
                    println("Error al eliminar jugador de partido")
                }
            }
        }
        return getMatchById(idMatch)
    }

    override suspend fun getPlaceOfPlayerInMatch(idMatch: Int, idPlayer: Int): Int {
        getMatchById(idMatch)?.let {
            when (idPlayer) {
                it.id_player1 -> return 1
                it.id_player2 -> return 2
                it.id_player3 -> return 3
                it.id_player4 -> return 4
                else -> {
                    return -1
                }
            }
        }
        return -1
    }

    override suspend fun getHistoryPlayer(playerId: Int): List<Match?> {
        return dbQuery {
            MatchTable.select {
                (MatchTable.id_player1 eq playerId) or
                        (MatchTable.id_player2 eq playerId) or
                        (MatchTable.id_player3 eq playerId) or
                        (MatchTable.id_player4 eq playerId)
            }.mapNotNull { rowToMatch(it) }
        }
    }

    /**** OPERACIONES CON RESULTADOS DE PARTIDOS *****/

    override suspend fun changeOpenState(idMatch: Int, open:Boolean): Match? {
        dbQuery {
            MatchTable.update({ MatchTable.id eq idMatch }) {
                it[MatchTable.open] = open
            }
        }
        return getMatchById(idMatch)
    }

    override suspend fun loadResults(idMatch: Int, params: ResultMatchParams): Match? {
        dbQuery {
            MatchTable.update({ MatchTable.id eq idMatch }) {
                it[MatchTable.scoreSet1A] = params.scoreSet1A
                it[MatchTable.scoreSet1B] = params.scoreSet1B
                it[MatchTable.scoreSet2A] = params.scoreSet2A
                it[MatchTable.scoreSet2B] = params.scoreSet2B
                it[MatchTable.scoreSet3A] = params.scoreSet3A
                it[MatchTable.scoreSet3B] = params.scoreSet3B
                it[MatchTable.confirmResult1] = false
                it[MatchTable.confirmResult2] = false
            }
        }
        return getMatchById(idMatch)
    }

    override suspend fun calculateResults(idMatch: Int): Match? {
        //Recogiendo los datos de los sets introducirlo en el resultado:
        val match = getMatchById(idMatch)
        val sets = listOf(
            Pair(match?.scoreSet1A, match?.scoreSet1B),
            Pair(match?.scoreSet2A, match?.scoreSet2B),
            Pair(match?.scoreSet3A, match?.scoreSet3B)
        )
        val result = sets.map { (a, b) ->
            when {
                a == null || b == null -> null
                a > b -> "A"
                a < b -> "B"
                else -> "D"
            }
        }
        val resultString = result.joinToString(separator = "-")
        dbQuery {
            MatchTable.update({ MatchTable.id eq idMatch }) {
                it[MatchTable.matchResult] = resultString
                it[MatchTable.open] = false
            }
        }
        return getMatchById(idMatch)

    }

    override suspend fun confirmOneResults(idMatch: Int): Match? {
        dbQuery {
            MatchTable.update({ MatchTable.id eq idMatch }) {
                it[MatchTable.confirmResult1] = true
            }
        }
        return getMatchById(idMatch)
    }

    override suspend fun confirmSecondResults(idMatch: Int): Match? {
        dbQuery {
            MatchTable.update({ MatchTable.id eq idMatch }) {
                it[MatchTable.confirmResult2] = true
            }
        }
        return getMatchById(idMatch)
    }

    /*** FUNCIONES UTILS ***/

    private fun UpdateBuilder<*>.setMatchParams(params: CreateMatchParams) {
        println("paramsMatchService: $params")
        if (params.id_player1 != null) this[MatchTable.id_player1] = params.id_player1
        if (params.id_player2 != null) this[MatchTable.id_player2] = params.id_player2
        if (params.id_player3 != null) this[MatchTable.id_player3] = params.id_player3
        if (params.id_player4 != null) this[MatchTable.id_player4] = params.id_player4
        if (params.scoreSet1A != null) this[MatchTable.scoreSet1A] = params.scoreSet1A
        if (params.scoreSet1B != null) this[MatchTable.scoreSet1B] = params.scoreSet1B
        if (params.scoreSet2A != null) this[MatchTable.scoreSet2A] = params.scoreSet2A
        if (params.scoreSet2B != null) this[MatchTable.scoreSet2B] = params.scoreSet2B
        if (params.scoreSet3A != null) this[MatchTable.scoreSet3A] = params.scoreSet3A
        if (params.scoreSet3B != null) this[MatchTable.scoreSet3B] = params.scoreSet3B
        if (params.matchResult != null) this[MatchTable.matchResult] = params.matchResult
        if (params.date != null) this[MatchTable.date] = params.date.toLocalDateTime()
        this[MatchTable.open] = params.open
        if (params.type != null) this[MatchTable.type] = params.type
        if (params.level != null) this[MatchTable.level] = params.level
        if (params.chat != null) this[MatchTable.chat] = params.chat
        if (params.court != null) this[MatchTable.court] = params.court
    }

    private fun rowToMatch(row: ResultRow?): Match? {
        return if (row == null) null
        else Match(
            id = row[MatchTable.id],
            id_player1 = row[MatchTable.id_player1],
            id_player2 = row[MatchTable.id_player2],
            id_player3 = row[MatchTable.id_player3],
            id_player4 = row[MatchTable.id_player4],
            scoreSet1A = row[MatchTable.scoreSet1A],
            scoreSet1B = row[MatchTable.scoreSet1B],
            scoreSet2A = row[MatchTable.scoreSet2A],
            scoreSet2B = row[MatchTable.scoreSet2B],
            scoreSet3A = row[MatchTable.scoreSet3A],
            scoreSet3B = row[MatchTable.scoreSet3B],
            matchResult = row[MatchTable.matchResult],
            date = row[MatchTable.date].toReadableFormat(),
            level = row[MatchTable.level],
            open = row[MatchTable.open],
            type = row[MatchTable.type],
            confirmResult1 = row[MatchTable.confirmResult1],
            confirmResult2 = row[MatchTable.confirmResult2],
            chat = row[MatchTable.chat],
            court = row[MatchTable.court]
        )
    }
}