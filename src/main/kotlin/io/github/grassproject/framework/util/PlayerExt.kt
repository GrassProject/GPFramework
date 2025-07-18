package io.github.grassproject.framework.util

import io.github.grassproject.framework.util.component.toMiniMessage
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

fun Player.sendMessage(adventure: BukkitAudiences, message: String) =
    adventure.player(this).sendMessage(message.toMiniMessage())

fun Player.sendMessage(adventure: BukkitAudiences, message: Component) =
    adventure.player(this).sendMessage(message)

/*
fun Player.sendMessage(adventure: BukkitAudiences, builder: () -> List<Component>) =
    builder().forEach { msg ->
        this.sendMessage(adventure, msg)
    }

fun Player.sendMessages(adventure: BukkitAudiences, builder: () -> List<String>) {
    builder().forEach { msg ->
        this.sendMessage(adventure, msg.toMiniMessage())
    }
}*/
