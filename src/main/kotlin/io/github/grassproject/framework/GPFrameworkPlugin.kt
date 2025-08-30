package io.github.grassproject.framework

import io.github.grassproject.framework.commands.GPFCommand
import io.github.grassproject.framework.config.GPConfig
import io.github.grassproject.framework.config.init
import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.database.DatabaseManager
import io.github.grassproject.framework.util.GPLogger

class GPFrameworkPlugin : GPPlugin() {
    companion object {
        lateinit var instance: GPFrameworkPlugin
            private set
    }

    override fun load() {
        instance = this
        saveResource("lang/ko.json", true)
        GPLogger.info("Loading Framework...")
    }

    override fun enable() {
        val config = GPConfig(dataFolder, "config.yml")
        init(config)
        DatabaseManager.initConfig(config)
        DatabaseManager.connect()

        GPLogger.info("GPFramework successfully enabled")

        GPFCommand()
    }

    override fun disable() {
        GPLogger.warning("GPFramework disabled")
    }
}