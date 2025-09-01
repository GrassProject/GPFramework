package io.github.grassproject.framework.core

import io.github.grassproject.framework.util.GPLogger
import io.github.grassproject.framework.util.bukkit.MinecraftVersion
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

abstract class GPPlugin : JavaPlugin(), GPFramework {
    val version = pluginMeta.version
    val authors = pluginMeta.authors
    val description = pluginMeta.description
    val apiVersion = pluginMeta.apiVersion
    lateinit var logger: GPLogger

    override fun onLoad() {
        logger = GPLogger(this)
        load()
    }

    override fun onEnable() {
        if (!MinecraftVersion.V1_21_1.isAbove()) {
            GPLogger.warning("서버 버전이 너무 낮습니다. 1.21.1 이상을 사용하세요.")
            server.pluginManager.disablePlugin(this)
            return
        }

        GPFrameworkEngine.register(this)
        enable()
    }

    override fun onDisable() {
        GPFrameworkEngine.unregister(this)
        disable()
    }

    fun registerListener(listener: Listener) {
        this.server.pluginManager.registerEvents(listener, this)
    }
}