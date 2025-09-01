package io.github.grassproject.framework.command

import io.github.grassproject.framework.core.GPPlugin
import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity

abstract class GPBasicCommand(
    private val instance: GPPlugin,
    private val name: String,
    private val aliases: List<String>,
    private val description: String,
    private val permission: String? = null
): BasicCommand {
    final override fun execute(commandSourceStack: CommandSourceStack, args: Array<out String>) {
        execute(commandSourceStack.sender, commandSourceStack.executor, args)
    }

    final override fun suggest(commandSourceStack: CommandSourceStack, args: Array<out String>): MutableCollection<String> {
        return tabComplete(commandSourceStack.sender, commandSourceStack.executor, args)
    }
    final override fun permission(): String? = permission
    final override fun canUse(sender: CommandSender): Boolean {
        return canExecute(sender)
    }
    abstract fun execute(sender: CommandSender, executor: Entity?, args: Array<out String>)

    abstract fun tabComplete(sender: CommandSender, executor: Entity?, args: Array<out String>): MutableCollection<String>
    abstract fun canExecute(sender: CommandSender): Boolean
    fun register() {
        instance.registerCommand(name, description, aliases, this)
    }

    companion object {
        @JvmStatic
        fun literal(
            instance: GPPlugin,
            name: String, aliases: List<String> = emptyList(), description: String = "",
            permission: String? = null,
            execute: (CommandSender, Array<out String>) -> Unit,
            tabComplete: (CommandSender, Array<out String>) -> MutableCollection<String> = { _, _ -> mutableListOf() },
            canExecute: (CommandSender) -> Boolean = { true }
        ): Boolean {
            return try {
                val command = object : GPBasicCommand(instance, name, aliases, description, permission) {
                    override fun execute(
                        sender: CommandSender, executor: Entity?,
                        args: Array<out String>
                    ) = execute(sender, args)

                    override fun tabComplete(
                        sender: CommandSender, executor: Entity?,
                        args: Array<out String>
                    ): MutableCollection<String> = tabComplete(sender, args)

                    override fun canExecute(sender: CommandSender): Boolean = canExecute(sender)
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
