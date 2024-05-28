package com.backtussam.services.league

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.db.tables.LeagueTable
import com.backtussam.model.League
import com.backtussam.utils.extensions.toISO8601Format
import com.backtussam.utils.extensions.toLocalDateTime
import com.backtussam.utils.extensions.toReadableFormat
import com.backtussam.utils.params.league.CreateLeagueParams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LeagueServiceImpl : LeagueService {

    override suspend fun getLeague(name: String): League? {
        return dbQuery {
            LeagueTable.select {
                LeagueTable.name eq name
            }.mapNotNull { rowToLeague(it) }
                .singleOrNull()
        }
    }

    override suspend fun getLeagueById(id: Int): League? {
        return dbQuery {
            LeagueTable.select {
                LeagueTable.id eq id
            }.mapNotNull { rowToLeague(it) }
                .singleOrNull()
        }
    }

    override suspend fun getLeagues(): List<League> {
        return dbQuery {
            LeagueTable.selectAll().mapNotNull { rowToLeague(it) }
        }
    }

    override suspend fun registerLeagueByName(name: String): League? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = LeagueTable.insert {
                it[LeagueTable.name] = name
            }
        }
        return rowToLeague(statement?.resultedValues?.get(0))
    }

    override suspend fun registerLeague(params: CreateLeagueParams): League? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = LeagueTable.insert {
                it[LeagueTable.name] = params.name
                it[LeagueTable.duration] = params.duration
                it[LeagueTable.ascent] = params.ascent
                it[LeagueTable.descent] = params.descent
                // Formato fecha "2024-05-15T10:12:55.205120600"
                it[LeagueTable.startDate] = LocalDateTime.parse(params.startDate, DateTimeFormatter.ISO_DATE_TIME)
                //Calcular la duracion en meses y sumarla a la fecha de inicio
                it[LeagueTable.endDate] = LocalDateTime.parse(params.startDate, DateTimeFormatter.ISO_DATE_TIME).plusMonths(params.duration.toLong())
            }
        }
        return rowToLeague(statement?.resultedValues?.get(0))
    }

    override suspend fun updateLeague(name: String, params: CreateLeagueParams): League? {
        val league = getLeague(name)
        if (league != null) {
            dbQuery {
                LeagueTable.update({ LeagueTable.name eq name }) {
                    it[LeagueTable.name] = params.name
                    it[LeagueTable.duration] = params.duration
                    it[LeagueTable.ascent] = params.ascent
                    it[LeagueTable.descent] = params.descent
                    it[LeagueTable.startDate] = params.startDate!!.toLocalDateTime()
                    it[LeagueTable.endDate] = params.startDate.toLocalDateTime().plusMonths(params.duration.toLong())
                }
            }
        }
        return getLeague(params.name)
    }


    override suspend fun deleteLeague(name: String): Boolean {
        return dbQuery {
            LeagueTable.deleteWhere { LeagueTable.name eq name } > 0
        }
    }

    //Función utilizada para mapear los datos de la base de datos a un objeto League
    private fun rowToLeague(row: ResultRow?): League? {
        return if (row == null) null
        else League(
            id = row[LeagueTable.id],
            name = row[LeagueTable.name],
            duration = row[LeagueTable.duration],
            startDate = row[LeagueTable.startDate].toReadableFormat(),
            ascent = row[LeagueTable.ascent],
            descent = row[LeagueTable.descent],
            endDate = row[LeagueTable.endDate].toReadableFormat()
        )
    }
}