package io.github.grassproject.framework.command

import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.exepction.CommandAlreadyRegistered
import io.github.grassproject.framework.exepction.CommandIsNotRegistered
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault
import org.bukkit.plugin.java.JavaPlugin

abstract class GPCommand<T : GPPlugin>(
    val plugin: T,
    name: String,
    aliases: List<String>,
    description: String,
    permission: String,
    permissionDefault: PermissionDefault = PermissionDefault.OP
) : Command(name) {
    init {
        this.aliases = aliases
        this.setDescription(description)
        this.permission = permission

        try {
            Bukkit.getServer().commandMap.register(name, this)
            Bukkit.getPluginManager().addPermission(Permission(permission, permissionDefault))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPlugin(): JavaPlugin = plugin

    fun register() {
        if (this.isRegistered) {
            throw CommandAlreadyRegistered()
        }
        this.register(Bukkit.getServer().commandMap)
    }

    fun unregister() {
        if (!this.isRegistered) {
            throw CommandIsNotRegistered()
        }
        this.unregister(Bukkit.getServer().commandMap)
    }

    final override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        return execute(sender, args)
    }
    abstract fun execute(sender: CommandSender, args: Array<out String>): Boolean

    final override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String?> {
        return tabComplete(sender, args)
    }
    abstract fun tabComplete(sender: CommandSender, args: Array<out String>): List<String?>

    companion object {
        @JvmStatic
        fun build(
            plugin: GPPlugin,
            name: String,
            aliases: List<String> = emptyList(),
            description: String = "",
            permission: String,
            permissionDefault: PermissionDefault = PermissionDefault.OP,
            execute: (sender: CommandSender, args: Array<out String>) -> Boolean,
            tabComplete: (sender: CommandSender, args: Array<out String>) -> List<String?> = { _, _ -> emptyList() }
        ): Boolean {
            return try {
                val command = object : GPCommand<GPPlugin>(
                    plugin, name, aliases,
                    description, permission,
                    permissionDefault
                ) {
                    override fun execute(
                        sender: CommandSender, args: Array<out String>
                    ): Boolean = execute(sender, args)

                    override fun tabComplete(
                        sender: CommandSender, args: Array<out String>
                    ): List<String?> = tabComplete(sender, args)
                }

                command.register()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

}