package com.backtussam.services.court

import com.backtussam.db.DatabaseFactory.dbQuery
import com.backtussam.db.tables.CourtTable
import com.backtussam.db.tables.LeagueTable
import com.backtussam.model.Court
import com.backtussam.model.League
import com.backtussam.utils.params.court.CreateCourtParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class CourtServiceImpl : CourtService {
    override suspend fun getCourts(): List<Court> {
        return dbQuery {
            CourtTable.selectAll().mapNotNull { rowToCourt(it) }
        }
    }

    override suspend fun getCourtByName(name: String): Court? {
        return dbQuery {
            CourtTable.select {
                CourtTable.name eq name
            }.mapNotNull { rowToCourt(it) }
                .singleOrNull()
        }
    }

    override suspend fun createCourt(params: CreateCourtParams): Court? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = CourtTable.insert {
                it[CourtTable.name] = params.name
                it[CourtTable.address] = params.address ?: ""
            }
        }
        return rowToCourt(statement?.resultedValues?.get(0))
    }

    override suspend fun updateCourt(name: String, params: CreateCourtParams): Court {
        return dbQuery {
            CourtTable.update({ CourtTable.name eq name }) {
                it[CourtTable.name] = params.name
                it[CourtTable.address] = params.address ?: ""
            }
            CourtTable.select {
                CourtTable.name eq params.name
            }.mapNotNull { rowToCourt(it) }
                .singleOrNull()!!
        }
    }

    override suspend fun deleteCourt(name: String): Boolean {
        return dbQuery {
            CourtTable.deleteWhere { CourtTable.name eq name } > 0
        }
    }

    //Función utilizada para mapear los datos de la base de datos a un objeto League
    private fun rowToCourt(row: ResultRow?): Court? {
        return if (row == null) null
        else Court(
            id = row[CourtTable.id],
            name = row[CourtTable.name],
            address = row[CourtTable.address],
        )
    }
}