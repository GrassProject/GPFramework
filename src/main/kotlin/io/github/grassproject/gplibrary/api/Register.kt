package io.github.grassproject.gplibrary.api

import org.bukkit.command.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

data class Register(val plugin: JavaPlugin) {

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