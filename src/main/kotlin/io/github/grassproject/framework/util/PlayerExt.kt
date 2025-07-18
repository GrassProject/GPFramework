package io.github.grassproject.framework.util

import io.github.grassproject.framework.util.component.toMiniMessage
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

fun Player.sendMessage(adventure: BukkitAudiences, message: String) =
    adventure.player(this).sendMessage(message.toMiniMessage())

fun Player.sendMessage(adventure: BukkitAudiences, message: Component) =
    adventure.player(this).sendMessage(message)

inline fun Player.sendMessage(adventure: BukkitAudiences, builder: () -> Component) =
    this.sendMessage(adventure, builder())

inline fun Player.sendMessage(adventure: BukkitAudiences, builder: () -> String) =
    this.sendMessage(adventure, builder().toMiniMessage())
