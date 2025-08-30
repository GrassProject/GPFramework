package io.github.grassproject.framework.util

import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.command.TabCompleter
import org.bukkit.command.TabExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

@Deprecated("Framework", level = DeprecationLevel.WARNING)
class Register(val plugin: JavaPlugin) {

    fun resisterEventListener(listener: Listener): Register {
        plugin.server.pluginManager.registerEvents(listener, plugin)
        return this
    }

    fun registerCommandExecutor(command: String, executor: CommandExecutor): Register {
        val cmd: PluginCommand = requireNotNull(plugin.getCommand(command)) {
            "Command '$command' not found in plugin.yml"
        }
        cmd.setExecutor(executor)
        return this
    }

    fun registerTabCompleter(command: String, completer: TabCompleter): Register {
        val cmd: PluginCommand = requireNotNull(plugin.getCommand(command)) {
            "Command '$command' not found in plugin.yml"
        }
        cmd.tabCompleter = completer
        return this
    }

    fun registerTabExecutor(command: String, executor: TabExecutor): Register {
        val cmd: PluginCommand = requireNotNull(plugin.getCommand(command)) {
            "Command '$command' not found in plugin.yml"
        }
        cmd.setExecutor(executor)
        cmd.tabCompleter = executor
        return this
    }
}