package io.github.grassproject.framework.core

import io.github.grassproject.framework.core.command.GPCommand
import io.github.grassproject.framework.core.events.GPListener
import org.bukkit.Bukkit
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault
import org.slf4j.LoggerFactory

interface IFramework {
    val plugin: GPPlugin
    val plugins: Map<String, GPPlugin>

    fun loadPlugin(plugin: GPPlugin)
    fun unloadPlugin(plugin: GPPlugin)

    fun load(plugin: GPPlugin)
    fun enable(plugin: GPPlugin)
    fun disable(plugin: GPPlugin)
    fun reload(plugin: GPPlugin)

    fun registerEvent(listener: GPListener<out GPPlugin>)
    fun registerCommand(name:String, command: GPCommand<out GPPlugin>)
    fun registerPermission(permission: String, permissionDefault: PermissionDefault)
}

object Framework : IFramework {

    private val logger = LoggerFactory.getLogger("GPFramework")

    override lateinit var plugin: GPPlugin
        private set

    override val plugins: MutableMap<String, GPPlugin> = mutableMapOf()

    override fun loadPlugin(plugin: GPPlugin) {
        logger.info("Loading plugin ${plugin.name}")
        plugins[plugin.name] = plugin
    }

    override fun unloadPlugin(plugin: GPPlugin) {
        logger.info("Unloading plugin ${plugin.name}")
        plugins.remove(plugin.name)
    }

    override fun load(plugin: GPPlugin) {
        this.plugin = plugin
    }

    override fun enable(plugin: GPPlugin) {
        Bukkit.getPluginManager().enablePlugin(plugin)
    }

    override fun disable(plugin: GPPlugin) {
        Bukkit.getPluginManager().disablePlugin(plugin)
    }

    override fun reload(plugin: GPPlugin) {
        disable(plugin)
        enable(plugin)
    }

    override fun registerEvent(listener: GPListener<out GPPlugin>) {
        Bukkit.getPluginManager().registerEvents(listener, listener.plugin)
    }

    override fun registerCommand(name:String, command: GPCommand<out GPPlugin>) {
        Bukkit.getServer().commandMap.register(name, command)
    }

    override fun registerPermission(permission: String, permissionDefault: PermissionDefault) {
        Bukkit.getPluginManager().addPermission(Permission(permission, permissionDefault))
    }


}