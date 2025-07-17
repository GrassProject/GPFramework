package io.github.grassproject.gplibrary.api.test

import io.github.grassproject.gplibrary.api.test.IGPPlugin
import io.github.grassproject.gplibrary.config.Config
import io.github.grassproject.gplibrary.util.bukkit.MinecraftVersion
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

abstract class GPPlugin : JavaPlugin(), IGPPlugin {

    final override val configManager: Config by lazy { Config(this) }
    final override val configYaml: YamlConfiguration
        get() = configManager.loadYaml("config.yml")

    override fun onEnable() {
        if (!MinecraftVersion.V1_21_1.isAbove()) {
            logger.warning("서버 버전이 너무 낮습니다. 1.21.1 이상을 사용하세요.")
            server.pluginManager.disablePlugin(this)
            return
        }

        setupConfig()
        enable()
    }

    override fun onDisable() {
        disable()
    }

    override fun reload() {
        disable()
        setupConfig()
        enable()
    }

    protected open fun setupConfig() {
        configManager.createFromResource("config.yml")
    }

    abstract override fun enable()
    abstract override fun disable()
}