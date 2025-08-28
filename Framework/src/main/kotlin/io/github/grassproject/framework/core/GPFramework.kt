package io.github.grassproject.framework.core

import org.bukkit.plugin.Plugin

interface GPFramework : Plugin {
    fun load()
    fun enable()
    fun disable()
}