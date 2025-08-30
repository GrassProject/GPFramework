package io.github.grassproject.framework.core

import io.github.grassproject.framework.util.GPLogger

object GPFrameworkEngine {
    private val plugins = mutableMapOf<String, GPPlugin>()

    fun register(plugin: GPPlugin) {
        plugins[plugin.name] = plugin
        GPLogger.suc("Registered plugin ${plugin.name}")
    }

    fun unregister(plugin: GPPlugin) {
        plugins.remove(plugin.name)
        GPLogger.bug("Unregistered plugin ${plugin.name}")
    }

    fun getPlugin(name: String): GPPlugin? = plugins[name]

    fun listPlugins(): List<String> = plugins.keys.toList()
}