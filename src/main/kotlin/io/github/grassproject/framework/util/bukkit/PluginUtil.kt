package io.github.grassproject.framework.util.bukkit

import org.bukkit.Bukkit

object PluginUtil {
    fun isEnabled(plugin: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(plugin)
    }

    fun checkPlugin(plugin: String): Boolean {
        val pluginInstance = Bukkit.getPluginManager().getPlugin(plugin)
        return pluginInstance != null
    }
}