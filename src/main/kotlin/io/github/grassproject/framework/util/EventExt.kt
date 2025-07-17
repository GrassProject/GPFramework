package io.github.grassproject.framework.util

import io.github.grassproject.framework.GPFrameworkPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.Listener

fun Listener.register() {
    GPFrameworkPlugin.instance.server.pluginManager.registerEvents(this, GPFrameworkPlugin.instance)
}

fun Event.call() {
    Bukkit.getServer().pluginManager.callEvent(this)
}