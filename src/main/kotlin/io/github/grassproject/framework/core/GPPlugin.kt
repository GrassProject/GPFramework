package io.github.grassproject.framework.core

import io.github.grassproject.framework.core.command.GPCommand
import io.github.grassproject.framework.core.listener.GPListener
import io.github.grassproject.framework.util.bukkit.MinecraftVersion
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

abstract class GPPlugin : JavaPlugin() {

//    TODO 차후 신설
//    val configManager: Config by lazy { Config(this) }
//    val configYaml: YamlConfiguration
//        get() = configManager.loadYaml("config.yml")
//
//    protected open fun setupConfig() {
//        configManager.createFromResource("config.yml")
//    }

    private val listeners = mutableSetOf<GPListener<out GPPlugin>>()

    lateinit var framework: IFramework
        private set
    lateinit var plugin: GPPlugin
        private set

    override fun onLoad() {
        framework = Framework
        load()
    }

    override fun onEnable() {
        if (!MinecraftVersion.V1_21_1.isAbove()) {
            logger.warning("서버 버전이 너무 낮습니다. 1.21.1 이상을 사용하세요.")
            server.pluginManager.disablePlugin(this)
            return
        }
        plugin = this
        framework.loadPlugin(this)
        enable()
    }

    override fun onDisable() {
        disable()
        framework.unloadPlugin(this)
    }

    protected open fun registerEvent(listener: GPListener<out GPPlugin>) {
        listeners.add(listener)
        Bukkit.getPluginManager().registerEvents(listener, this)
    }

    fun registerEvents() {
        listeners.forEach { Bukkit.getPluginManager().registerEvents(it, this) }
    }

    fun unregisterEvents() {
        HandlerList.getHandlerLists().forEach { it.unregister(this) }
    }

    fun setupBStats(id: Int) {
        Metrics(this, id)
    }

    protected open fun load() {}
    protected open fun enable() {}
    protected open fun disable() {}
}