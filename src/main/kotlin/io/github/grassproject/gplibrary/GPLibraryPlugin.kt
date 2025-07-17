package io.github.grassproject.gplibrary

import io.github.grassproject.gplibrary.config.Config
import io.github.grassproject.gplibrary.config.IConfig
import io.github.grassproject.gplibrary.listener.InventoryListener
import io.github.grassproject.gplibrary.util.register
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

class GPLibraryPlugin : JavaPlugin(), IConfig {

    companion object {
        lateinit var instance: GPLibraryPlugin
            private set
    }

    override val configManager: Config by lazy { Config(this) }
    override val configYaml: YamlConfiguration
        get() = configManager.loadYaml("config.yml")

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        setupConfig()
        InventoryListener().register()
    }

    private fun setupConfig() {
        configManager.createFromResource("config.yml")
    }
}
