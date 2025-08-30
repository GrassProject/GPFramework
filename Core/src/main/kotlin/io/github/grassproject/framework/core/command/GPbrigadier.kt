package io.github.grassproject.framework.core.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.plugin.java.JavaPlugin

@Deprecated("Experimental")
abstract class GPBrigadier {

    abstract val plugin: JavaPlugin
    abstract val commandName: String
    open val permission: String? = null

    protected val rootBuilder: LiteralArgumentBuilder<CommandSourceStack> by lazy {
        LiteralArgumentBuilder.literal<CommandSourceStack>(commandName)
    }

    val commandNode: LiteralCommandNode<CommandSourceStack>
        get() = rootBuilder.build()

    fun literal(name: String, block: LiteralArgumentBuilder<CommandSourceStack>.() -> Unit) {
        val builder = LiteralArgumentBuilder.literal<CommandSourceStack>(name)
        builder.block()
        rootBuilder.then(builder)
    }

    fun <T> argument(name: String, type: ArgumentType<T>, executes: ((CommandSourceStack, ArgumentType<T>) -> Int)? = null) {
        val argBuilder = com.mojang.brigadier.builder.RequiredArgumentBuilder.argument<CommandSourceStack, T>(name, type)
        if (executes != null) {
            argBuilder.executes { ctx ->
                val value = ctx.getArgument(name, type.javaClass)
                executes(ctx.source, value)
            }
        }
        rootBuilder.then(argBuilder)
    }

    open fun executes(exec: (CommandSourceStack, Array<String>) -> Int) {
        rootBuilder.executes { ctx ->
            val args = ctx.input.split(" ").drop(1).toTypedArray()
            exec(ctx.source, args)
        }
    }

    fun register(): LiteralCommandNode<CommandSourceStack> {
//        plugin.server.pluginManager.registerEvents(object : org.bukkit.event.Listener {}, plugin)
//        plugin.getLifecycleManager().registerEventHandler(io.papermc.paper.command.brigadier.) { commands ->
//            commands.registrar().register(commandNode, "GPBrigadier: $commandName")
//        }
        return this.rootBuilder.build()
    }
}