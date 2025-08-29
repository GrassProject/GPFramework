package io.github.grassproject.framework.database

import com.zaxxer.hikari.HikariConfig

class MySQLDriver(
    host: String,
    port: Int,
    database: String,
    username: String,
    password: String,
    maxPoolSize: Int = 10
) : DatabaseManager.DataDriver(HikariConfig().apply {
    this.driverClassName = "com.mysql.cj.jdbc.Driver"
    this.jdbcUrl = "jdbc:mysql://$host:$port/$database?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
    this.username = username
    this.password = password
    this.maximumPoolSize = maxPoolSize
    this.poolName = "gpframework"
})