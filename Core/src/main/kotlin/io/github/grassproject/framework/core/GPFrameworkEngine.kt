package io.github.grassproject.framework.core

import io.github.grassproject.framework.events.GPPluginUnregisterEvent
import io.github.grassproject.framework.events.GPPluginRegisterEvent
import io.github.grassproject.framework.exepction.FailToCallEvent
import io.github.grassproject.framework.util.GPLogger

object GPFrameworkEngine {
    private val plugins = mutableMapOf<String, GPPlugin>()

    @JvmStatic
    fun register(plugin: GPPlugin) {
        plugins[plugin.name] = plugin
        GPLogger.suc("Registered plugin ${plugin.name}")

        val event = GPPluginRegisterEvent(plugin)
        if (!event.callEvent()) throw FailToCallEvent(event.name)
    }

    @JvmStatic
    fun unregister(plugin: GPPlugin) {
        plugins.remove(plugin.name)
        GPLogger.warning("Unregistered plugin ${plugin.name}")

        val event = GPPluginUnregisterEvent(plugin)
        if (!event.callEvent()) throw FailToCallEvent(event.name)
    }

    @JvmStatic
    fun getPlugin(name: String): GPPlugin? = plugins[name]

    @JvmStatic
    fun listPlugins(): List<String> = plugins.keys.toList()
}