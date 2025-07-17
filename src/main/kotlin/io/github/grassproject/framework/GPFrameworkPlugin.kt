package io.github.grassproject.framework

import io.github.grassproject.framework.config.Config
import io.github.grassproject.framework.config.IConfig
import io.github.grassproject.framework.listener.InventoryListener
import io.github.grassproject.framework.util.register
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

class GPFrameworkPlugin : JavaPlugin(), IConfig {

    companion object {
        lateinit var instance: GPFrameworkPlugin
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
