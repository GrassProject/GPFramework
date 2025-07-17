package io.github.grassproject.framework.util

import io.github.grassproject.framework.GPFrameworkPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

fun Listener.register() {
    GPFrameworkPlugin.instance.server.pluginManager.registerEvents(this, GPFrameworkPlugin.instance)
}

fun Event.call() {
    Bukkit.getServer().pluginManager.callEvent(this)
}

inline fun <reified T : Event> event(
    crossinline handler: T.() -> Unit
): RegisteredEvent {
    val plugin = JavaPlugin.getProvidingPlugin(object {}::class.java)
    val listener = object : Listener {
        @EventHandler
        fun onEvent(event: T) {
            handler(event)
        }
    }
    Bukkit.getPluginManager().registerEvents(listener, plugin)
    return RegisteredEvent(listener)
}

class RegisteredEvent(private val listener: Listener) {
    fun unregister() {
        HandlerList.unregisterAll(listener)
    }
}