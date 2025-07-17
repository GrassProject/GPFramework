package io.github.grassproject.gplibrary.util

import io.github.grassproject.gplibrary.GPLibraryPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.Listener

fun Listener.register() {
    GPLibraryPlugin.instance.server.pluginManager.registerEvents(this, GPLibraryPlugin.instance)
}

fun Event.call() {
    Bukkit.getServer().pluginManager.callEvent(this)
}