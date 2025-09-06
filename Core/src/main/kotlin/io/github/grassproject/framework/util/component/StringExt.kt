package io.github.grassproject.framework.util.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

fun String.toMiniMessage(): Component =
    MiniMessage.miniMessage().deserialize(
        this
            .replace("§","&")
            .replace("&a", "<green>")
            .replace("&c", "<red>")
            .replace("&b", "<aqua>")
            .replace("&e", "<yellow>")
            .replace("&6", "<gold>")
            .replace("&d", "<light_purple>")
            .replace("&f", "<white>")
            .replace("&3", "<dark_aqua>")
            .replace("&9", "<blue>")
            .replace("&7", "<gray>")
            .replace("&8", "<dark_gray>")
            .replace("&4", "<dark_red>")
            .replace("&1", "<dark_blue>")
            .replace("&4", "<dark_red>")
            .replace("&8", "<dark_gray>")
            .replace("&2", "<dark_green>")
            .replace("&5", "<dark_purple>")
            .replace("&0", "<black>")
            .replace("&k", "<obfuscated>")
            .replace("&l", "<bold>")
            .replace("&m", "<strikethrough>")
            .replace("&n", "<underlined>")
            .replace("&o", "<italic>")
            .replace("&r", "<reset>")
    )

fun String.toComponent(): Component =
    Component.text(this)