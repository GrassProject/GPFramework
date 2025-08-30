package io.github.grassproject.framework.core

import io.github.grassproject.framework.util.GPLogger

object GPFrameworkEngine {
    private val plugins = mutableMapOf<String, GPPlugin>()

    @JvmStatic
    fun register(plugin: GPPlugin) {
        plugins[plugin.name] = plugin
        GPLogger.suc("Registered plugin ${plugin.name}")
    }

    @JvmStatic
    fun unregister(plugin: GPPlugin) {
        plugins.remove(plugin.name)
        GPLogger.bug("Unregistered plugin ${plugin.name}")
    }

    @JvmStatic
    fun getPlugin(name: String): GPPlugin? = plugins[name]

    @JvmStatic
    fun listPlugins(): List<String> = plugins.keys.toList()
}