package io.github.grassproject.framework

import io.github.grassproject.framework.commands.GPFCommand
import io.github.grassproject.framework.config.GPFile
import io.github.grassproject.framework.config.init
import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.database.DatabaseManager
import io.github.grassproject.framework.github.GithubAPI
import io.github.grassproject.framework.message.GPFTranslate

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
            GPFTranslate.fromList(
                "framework.find.update", mapOf(
                    "data" to data["published"].asString,
                    "new" to data["ver"].asString, "now" to this.version,
                    "link" to data["download"].asString
            )).forEach { logger.bug(it) }
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