package io.github.grassproject.framework.api.test

interface IFramework {
    val plugin: GPPlugin
    val plugins: Map<String, GPPlugin>

    fun loadPlugin(plugin: GPPlugin)
    fun unloadPlugin(plugin: GPPlugin)

    fun load(plugin: GPPlugin)
    fun enable(plugin: GPPlugin)
    fun disable(plugin: GPPlugin)
}