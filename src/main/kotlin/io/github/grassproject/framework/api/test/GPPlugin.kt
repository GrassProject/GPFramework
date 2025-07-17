package io.github.grassproject.framework.api.test

import io.github.grassproject.framework.config.Config
import io.github.grassproject.framework.util.BStatsUtils
import io.github.grassproject.framework.util.bukkit.MinecraftVersion
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

abstract class GPPlugin : JavaPlugin(), IGPPlugin {

    final override val configManager: Config by lazy { Config(this) }
    final override val configYaml: YamlConfiguration
        get() = configManager.loadYaml("config.yml")

    override fun onLoad() {
        load()
    }

    override fun onEnable() {
        if (!MinecraftVersion.V1_21_1.isAbove()) {
            logger.warning("서버 버전이 너무 낮습니다. 1.21.1 이상을 사용하세요.")
            server.pluginManager.disablePlugin(this)
            return
        }

        enable()
    }

    override fun onDisable() {
        disable()
    }

    protected open fun setupConfig() {
        configManager.createFromResource("config.yml")
    }

    protected open fun setupBStats(id: Int) {
        BStatsUtils(id)
    }

    override fun load() {}
    override fun enable() {}
    override fun disable() {}
}