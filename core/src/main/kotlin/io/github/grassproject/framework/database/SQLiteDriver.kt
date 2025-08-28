package io.github.grassproject.framework.database

import com.zaxxer.hikari.HikariConfig

class SQLiteDriver(
    databasePath: String,
    maxPoolSize: Int = 1
) : DatabaseManager.DataDriver(HikariConfig().apply {
    this.driverClassName = "org.sqlite.JDBC"
    this.jdbcUrl = "jdbc:sqlite:$databasePath"
    this.maximumPoolSize = maxPoolSize
    this.poolName = "gpframework"
})