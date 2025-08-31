package io.github.grassproject.framework.commands

import io.github.grassproject.framework.GPFrameworkPlugin
import io.github.grassproject.framework.core.GPFrameworkEngine
import io.github.grassproject.framework.core.command.GPCommand
import io.github.grassproject.framework.util.component.toMiniMessage
import io.github.grassproject.framework.utils.translate
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender

class GPFCommand: GPCommand<GPFrameworkPlugin>(
    GPFrameworkPlugin.instance,
    "gpf", listOf("gpframework"),
    "", "gp.framework.commands"
) {
    private val prefix = "<#96f19c>GPF</#96f19c><#989c99> | </#989c99>".toMiniMessage()
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size == 1) {
            if (args[0]=="info") {
                translate.fromList(
                    "command.gpf.info", mapOf(
                        "server" to sender.server.name,
                        "version" to sender.server.version,
                        "nms" to sender.server.bukkitVersion,
                        "name" to plugin.name,
                        "ver" to plugin.version,
                        "list" to GPFrameworkEngine.listPlugins().joinToString(", ")
                    )
                ).forEach { sender.sendMessage(prefix.append { it.toMiniMessage() }) }
            }

            if (args[0]=="") {

            }
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        val tab = mutableListOf<String>()
        if (args.size==1) {
            tab.addAll(arrayOf("info"))
        }
        return tab
    }
//
// sorted()
//    fun listPluginsColored(): List<String> {
//        val serializer = LegacyComponentSerializer.legacySection()
//        return GPFrameworkEngine.listPlugins().map { name ->
//            serializer.serialize(
//                Component.text(name, if (GPFrameworkEngine.getPlugin(name) is GPFrameworkPlugin) NamedTextColor.GREEN else NamedTextColor.GRAY)
//            )
//        }
//    }
}