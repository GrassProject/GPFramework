package io.github.grassproject.framework.util.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun Component.toMiniMessageString(): String =
    MiniMessage.miniMessage().serialize(this)

fun Component.toPlain(): String =
    PlainTextComponentSerializer.plainText().serialize(this)