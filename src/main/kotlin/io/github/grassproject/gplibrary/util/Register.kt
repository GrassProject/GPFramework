package io.github.grassproject.gplibrary.util

import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.command.TabCompleter
import org.bukkit.command.TabExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.Objects

class Register(val plugin: JavaPlugin) {

    fun resistEventListener(listener: Listener): Register {
        plugin.server.pluginManager.registerEvents(listener, plugin)
        return this
    }

    fun resistCommandExecutor(command: String, executor: CommandExecutor?): Register {
        Objects.requireNonNull<PluginCommand>(plugin.getCommand(command)).setExecutor(executor)
        return this
    }

    fun resistTabCompleter(command: String, completer: TabCompleter?): Register {
        Objects.requireNonNull<PluginCommand>(plugin.getCommand(command)).tabCompleter = completer
        return this
    }

    fun resistTabExecutor(command: String, executor: TabExecutor?): Register {
        Objects.requireNonNull<PluginCommand>(plugin.getCommand(command)).setExecutor(executor)
        Objects.requireNonNull<PluginCommand>(plugin.getCommand(command)).tabCompleter = executor
        return this
    }

}