package io.github.grassproject.framework.util

import io.github.grassproject.framework.util.component.toMiniMessage
import org.bukkit.Bukkit

object GPLogger {
    private val prefix="<#96f19c>GPFramework</#96f19c><#989c99> > </#989c99>"

    @JvmStatic
    fun info(string: String) {
        Bukkit.getConsoleSender().sendMessage { "${prefix}<white>${string}</white>".toMiniMessage() }
    }

    @JvmStatic
    fun warning(string: String) {
        Bukkit.getConsoleSender().sendMessage { "${prefix}<yellow>${string}</yellow>".toMiniMessage() }
    }

    @JvmStatic
    fun suc(string: String) {
        Bukkit.getConsoleSender().sendMessage { "${prefix}<green>${string}</green>".toMiniMessage() }
    }

    @JvmStatic
    fun bug(string: String) {
        Bukkit.getConsoleSender().sendMessage { "${prefix}<red>${string}</red>".toMiniMessage() }
    }
}