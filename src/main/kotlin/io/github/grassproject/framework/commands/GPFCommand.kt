package io.github.grassproject.framework.commands

import io.github.grassproject.framework.GPFrameworkPlugin
import io.github.grassproject.framework.core.GPFrameworkEngine
import io.github.grassproject.framework.command.GPCommand
import io.github.grassproject.framework.message.GPFTranslate
import io.github.grassproject.framework.util.McLogsUtil
import io.github.grassproject.framework.util.component.toMiniMessage
import org.bukkit.command.CommandSender

class GPFCommand: GPCommand<GPFrameworkPlugin>(
    GPFrameworkPlugin.instance,
    "gpf", listOf("gpframework"),
    "", "gp.framework.commands"
) {
    private val prefix = "<#96f19c>GPF</#96f19c><#989c99> | </#989c99>".toMiniMessage()
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.isEmpty()) return true

        when (args[0].lowercase()) {
            "info" -> info(sender)
            "dump" -> dump(sender)
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        val tab = mutableListOf<String>()
        if (args.size == 1) {
            tab.addAll(arrayOf("info", "dump"))
        }
        return tab
    }

    private fun info(sender: CommandSender) {
        GPFTranslate.fromList(
            "command.gpf.info",
            mapOf(
                "server" to sender.server.name,
                "version" to sender.server.version,
                "nms" to sender.server.bukkitVersion,
                "name" to plugin.name,
                "ver" to plugin.version,
                "list" to GPFrameworkEngine.listPlugins().joinToString(", ")
            )
        ).forEach { sender.sendMessage(prefix.append { it.toMiniMessage() }) }
    }

    private fun dump(sender: CommandSender) {
        val link = McLogsUtil.uploadLogs()
        val message = if (link != null)
            "<green>Log dump successful</green> <gray><u><click:open_url:'$link'>$link</click></u></gray>"
        else "<red>Log dump failed</red>"

        sender.sendMessage(prefix.append { message.toMiniMessage() })
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