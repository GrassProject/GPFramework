package io.github.grassproject.framework.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.grassproject.framework.config.ConfigFile
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

object DatabaseManager {

    private var driver: DataDriver? = null

    @JvmStatic
    fun init(driver: DataDriver) {
        this.driver = driver
    }

    @JvmStatic
    fun initConfig(config: ConfigFile) {
        val type = config.enum("database.type", DataType.SQLITE)

        val host = config.string("database.credentials.host", "localhost")
        val port = config.int("database.credentials.port", 3306)
        val database = config.string("database.credentials.database", "database")
        val username = config.string("database.credentials.username", "root")
        val password = config.string("database.credentials.password", "password")
        val maxPoolSize = config.int("database.pool_options.size", 10)

        driver = when (type) {
            DataType.MYSQL -> MySQLDriver(host, port, database, username, password, maxPoolSize)
            DataType.SQLITE -> buildSQLiteDriver(database, maxPoolSize)
        }
    }

    @JvmStatic
    fun connect() = driver?.connect()
        ?: throw IllegalStateException("Database driver not initialized")

    @JvmStatic
    fun close() {
        driver?.close()
        driver = null
    }

    fun createTables(vararg tables: Table) {
        transaction(connect()) {
            SchemaUtils.create(*tables)
        }
    }

//    private fun buildSQLiteDriver(pluginFolder: File, databaseName: String, maxPoolSize: Int): DataDriver {
//        // val dbFile = GPFile(pluginFolder, "${databaseName}.db")
//        val dbFile = File("gpframework-database", "${databaseName}.db")
//        val databasePath = dbFile.absolutePath
//        return SQLiteDriver(databasePath, maxPoolSize)
//    }

    private fun buildSQLiteDriver(databaseName: String, maxPoolSize: Int): DataDriver {
        val folder = File("gpframework-database").also { it.mkdirs() }
        val databaseFile = File(folder, "$databaseName.db").apply { createNewFile() }
        return SQLiteDriver(databaseFile.absolutePath, maxPoolSize)
    }

    abstract class DataDriver(protected val config: HikariConfig) {
        private val dataSource: HikariDataSource by lazy { HikariDataSource(config) }

        fun connect(): Database = Database.connect(dataSource)

        fun close() {
            if (!dataSource.isClosed) dataSource.close()
        }
    }

    enum class DataType {
        MYSQL, SQLITE
    }
}