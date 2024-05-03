package com.backtussam.db

import com.backtussam.db.tables.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(
                ChatTable,
                CourtTable,
                LeagueTable,
                MatchTable,
                NotificationTable,
                PlayerLeagueHistoricalTable,
                PlayerMatchesHistoricalTable,
                PlayerNotificationTable,
                PlayerTable,
                RoleTable
            )
        }
    }
    private fun hikari(): HikariDataSource {
        //Crear un objeto HijariConfig
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        //Instanciar la ruta para la base de datos -> DireccionBD, nombreBD, usuario y contraseña
        //Importante el nombreDB en minusculas
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/bd-padel-tussam?user=julian&password=admin"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)

    }

    suspend fun <T> dbQuery(block: () -> T ): T = withContext(Dispatchers.IO){
        transaction {
            block()
        }
    }


}