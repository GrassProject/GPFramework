package io.github.grassproject.framework.core

import org.slf4j.LoggerFactory

object GPFrameworkEngine {

    private val logger = LoggerFactory.getLogger("GPFramework")
    private val plugins = mutableMapOf<String, GPPlugin>()

    fun register(plugin: GPPlugin) {
        plugins[plugin.name] = plugin
        logger.info("Registered plugin ${plugin.name}")
    }

    fun unregister(plugin: GPPlugin) {
        plugins.remove(plugin.name)
        logger.info("Unregistered plugin ${plugin.name}")
    }

    fun getPlugin(name: String): GPPlugin? = plugins[name]

    fun listPlugins(): List<String> = plugins.keys.toList()
}