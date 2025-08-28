package io.github.grassproject.framework.core.events

import io.github.grassproject.framework.core.GPPlugin
import org.bukkit.event.Listener

// Listener 흠..
abstract class GPListener<T : GPPlugin>(
    val plugin: T,
) : Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}