package io.github.grassproject.framework

import io.github.grassproject.framework.commands.GPFCommand
import io.github.grassproject.framework.config.GPFile
import io.github.grassproject.framework.config.init
import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.database.DatabaseManager
import io.github.grassproject.framework.github.GithubAPI

class GPFrameworkPlugin : GPPlugin() {
    companion object {
        lateinit var instance: GPFrameworkPlugin
            private set
    }

    override fun load() {
        instance = this
        saveResource("language/ko.json", true)
        logger.info("Loading Framework...")
    }

    override fun enable() {
        if (GithubAPI.isLatest(this)) {
            val data = GithubAPI.getLatest()
            logger.bug("New Version Released! ${data["published"]}")
            logger.bug("New: ${data["ver"]}, Now: $version")
        }

        val config = GPFile(dataFolder, "config.yml")
        init(config)
        DatabaseManager.initConfig(config)
        DatabaseManager.connect()

        logger.info("GPFramework successfully Enabled")
        logger.info(" | Version: <green>${version}</green>")

        GPFCommand()
    }

    override fun disable() {
        logger.warning("GPFramework disabled")
    }
}