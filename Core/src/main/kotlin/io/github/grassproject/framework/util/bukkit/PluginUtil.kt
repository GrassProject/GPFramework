package io.github.grassproject.framework.util.bukkit

import org.bukkit.Bukkit

object PluginUtil {
    @JvmStatic
    fun isEnabled(plugin: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(plugin)
    }

    @JvmStatic
    fun checkPlugin(plugin: String): Boolean {
        val pluginInstance = Bukkit.getPluginManager().getPlugin(plugin)
        return pluginInstance != null
    }

    @JvmStatic
    fun enablePlugin(plugin: String) {
        Bukkit.getPluginManager().enablePlugin(
            Bukkit.getPluginManager().getPlugin(plugin) ?: return
        )
    }

    @JvmStatic
    fun disablePlugin(plugin: String) {
        Bukkit.getPluginManager().disablePlugin(
            Bukkit.getPluginManager().getPlugin(plugin) ?: return
        )
    }
}