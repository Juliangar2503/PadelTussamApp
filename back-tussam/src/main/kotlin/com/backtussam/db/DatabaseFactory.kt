package com.backtussam.db

import com.backtussam.db.tables.*
import com.backtussam.utils.SeedData
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
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
                PlayerNotificationTable,
                PlayerTable,
                RoleTable
            )
            SeedData.init()
        }
    }


    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        val appConfig = HoconApplicationConfig(ConfigFactory.load())

        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = appConfig.propertyOrNull("datasource.jdbcUrl")?.getString()
            ?: "jdbc:postgresql://postgres:5432/bdpadeltussam"
        config.username = appConfig.propertyOrNull("datasource.username")?.getString() ?: "julian"
        config.password = appConfig.propertyOrNull("datasource.password")?.getString() ?: "admin"
        config.maximumPoolSize = appConfig.propertyOrNull("datasource.maximumPoolSize")?.getString()?.toInt() ?: 3
        config.isAutoCommit = appConfig.propertyOrNull("datasource.autoCommit")?.getString()?.toBoolean() ?: false
        config.transactionIsolation = appConfig.propertyOrNull("datasource.transactionIsolation")?.getString()
            ?: "TRANSACTION_REPEATABLE_READ"

        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T ): T = withContext(Dispatchers.IO){
        transaction {
            block()
        }
    }


}