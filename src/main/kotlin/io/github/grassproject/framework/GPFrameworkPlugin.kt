package io.github.grassproject.framework

import io.github.grassproject.framework.config.GPFile
import io.github.grassproject.framework.config.init
import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.database.DatabaseManager

class GPFrameworkPlugin : GPPlugin() {

    companion object {
        lateinit var instance: GPFrameworkPlugin
            private set
    }

    override fun load() {
        instance = this
    }

    override fun enable() {
        val config = GPFile(dataFolder, "config.yml")
        init(config)
        DatabaseManager.initConfig(config)
        DatabaseManager.connect()
    }

    override fun disable() {}
}