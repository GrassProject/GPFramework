package io.github.grassproject.framework.util

import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.util.component.toMiniMessage
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class GPLogger(private val plugin: GPPlugin? = null) {
    private val prefix = "<#96f19c>{gp-prefix}</#96f19c><#989c99> > </#989c99>"

    private fun format(color: String, message: String): Component {
        val name = plugin?.name ?: "GPFramework"
        return "${prefix.replace("{gp-prefix}", name)}<$color>$message</$color>".toMiniMessage()
    }

    fun info(string: String) = send("white", string)
    fun warning(string: String) = send("yellow", string)
    fun suc(string: String) = send("green", string)
    fun bug(string: String) = send("red", string)

    private fun send(color: String, string: String) {
        Bukkit.getConsoleSender().sendMessage(format(color, string))
    }

    companion object {
        @JvmStatic
        @JvmName("log")
        fun info(string: String) = GPLogger().info(string)

        @JvmStatic
        @JvmName("logSuc")
        fun suc(string: String) = GPLogger().suc(string)

        @JvmStatic
        @JvmName("warn")
        fun warning(string: String) = GPLogger().warning(string)

        @JvmStatic
        @JvmName("severe")
        fun bug(string: String) = GPLogger().bug(string)
    }
}
