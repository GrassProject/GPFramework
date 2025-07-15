package io.github.grassproject.gplibrary.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import kotlin.collections.get
import kotlin.text.iterator

object StringExt {
    private val miniMessage = MiniMessage.miniMessage()

    private val COLOR_TO_LEGACY = mapOf(
        NamedTextColor.BLACK to "§0",
        NamedTextColor.DARK_BLUE to "§1",
        NamedTextColor.DARK_GREEN to "§2",
        NamedTextColor.DARK_AQUA to "§3",
        NamedTextColor.DARK_RED to "§4",
        NamedTextColor.DARK_PURPLE to "§5",
        NamedTextColor.GOLD to "§6",
        NamedTextColor.GRAY to "§7",
        NamedTextColor.DARK_GRAY to "§8",
        NamedTextColor.BLUE to "§9",
        NamedTextColor.GREEN to "§a",
        NamedTextColor.AQUA to "§b",
        NamedTextColor.RED to "§c",
        NamedTextColor.LIGHT_PURPLE to "§d",
        NamedTextColor.YELLOW to "§e",
        NamedTextColor.WHITE to "§f"
    )

    private val LEGACY_TO_MINI = mapOf(
        "§a" to "<green>",
        "§c" to "<red>",
        "§b" to "<aqua>",
        "§e" to "<yellow>",
        "§6" to "<gold>",
        "§d" to "<light_purple>",
        "§f" to "<white>",
        "§3" to "<dark_aqua>",
        "§9" to "<blue>",
        "§7" to "<gray>",
        "§8" to "<dark_gray>",
        "§4" to "<dark_red>",
        "§1" to "<dark_blue>",
        "§2" to "<dark_green>",
        "§5" to "<dark_purple>"
    )

    private val DECORATION_CODES = mapOf(
        TextDecoration.BOLD to "§l",
        TextDecoration.ITALIC to "§o",
        TextDecoration.UNDERLINED to "§n",
        TextDecoration.STRIKETHROUGH to "§m",
        TextDecoration.OBFUSCATED to "§k"
    )

    fun toLegacyString(component: Component): String {
        val result = StringBuilder()
        processComponent(component, result, Style.empty())
        return result.toString()
    }

    private fun processComponent(component: Component, result: StringBuilder, defaultColor: TextColor? = null, parentStyle: Style) {
        when (component) {
            is TextComponent -> {
                val style = component.style().merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET)
                val color = style.color() ?: defaultColor
                result.append(COLOR_TO_LEGACY[color] ?: "§f")
                DECORATION_CODES.forEach { (decoration, code) ->
                    if (style.decoration(decoration) == TextDecoration.State.TRUE) result.append(code)
                }
                result.append(component.content())
                component.children().forEach { processComponent(it, result, color, style) }
            }
            else -> component.children().forEach { processComponent(it, result, defaultColor, parentStyle) }
        }
    }

    private fun processComponent(component: Component, result: StringBuilder, parentStyle: Style) {
        when (component) {
            is TextComponent -> {
                val style = component.style().merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET)
                val color = style.color()
                if (color != null) {
                    result.append(COLOR_TO_LEGACY[color] ?: "")
                }
                DECORATION_CODES.forEach { (decoration, code) ->
                    if (style.decoration(decoration) == TextDecoration.State.TRUE) {
                        result.append(code)
                    }
                }
                result.append(component.content())
                component.children().forEach { processComponent(it, result, style) }
            }
            else -> component.children().forEach { processComponent(it, result, parentStyle) }
        }
    }


    fun toMiniMessage(string: String): Component {
        var result = string.replace("&", "§")
        LEGACY_TO_MINI.forEach { (legacy, mini) ->
            result = result.replace(legacy, mini)
        }
        return miniMessage.deserialize(result)
    }

    fun toComponent(input: String): Component {
        val components = mutableListOf<Component>()
        val currentText = StringBuilder()
        var color: TextColor = NamedTextColor.WHITE
        var bold = false
        var italic = false
        var underlined = false
        var strikethrough = false
        var obfuscated = false
        var isCode = false

        for (character in input.replace("&", "§")) {
            if (character == '§') {
                if (currentText.isNotEmpty()) {
                    components.add(createComponent(currentText.toString(), color, bold, italic, underlined, strikethrough, obfuscated))
                    currentText.clear()
                }
                isCode = true
                continue
            }

            if (isCode) {
                when (character) {
                    'l' -> bold = true
                    'o' -> italic = true
                    'n' -> underlined = true
                    'm' -> strikethrough = true
                    'k' -> obfuscated = true
                    'r' -> {
                        color = NamedTextColor.WHITE
                        bold = false
                        italic = false
                        underlined = false
                        strikethrough = false
                        obfuscated = false
                    }
                    '0' -> color = NamedTextColor.BLACK
                    '1' -> color = NamedTextColor.DARK_BLUE
                    '2' -> color = NamedTextColor.DARK_GREEN
                    '3' -> color = NamedTextColor.DARK_AQUA
                    '4' -> color = NamedTextColor.DARK_RED
                    '5' -> color = NamedTextColor.DARK_PURPLE
                    '6' -> color = NamedTextColor.GOLD
                    '7' -> color = NamedTextColor.GRAY
                    '8' -> color = NamedTextColor.DARK_GRAY
                    '9' -> color = NamedTextColor.BLUE
                    'a' -> color = NamedTextColor.GREEN
                    'b' -> color = NamedTextColor.AQUA
                    'c' -> color = NamedTextColor.RED
                    'd' -> color = NamedTextColor.LIGHT_PURPLE
                    'e' -> color = NamedTextColor.YELLOW
                    'f' -> color = NamedTextColor.WHITE
                }
                isCode = false
                continue
            }

            currentText.append(character)
        }

        if (currentText.isNotEmpty()) {
            components.add(createComponent(currentText.toString(), color, bold, italic, underlined, strikethrough, obfuscated))
        }

        return components.fold(Component.empty()) { acc, component -> acc.append(component) }
    }

    private fun createComponent(
        text: String,
        color: TextColor,
        bold: Boolean,
        italic: Boolean,
        underlined: Boolean,
        strikethrough: Boolean,
        obfuscated: Boolean
    ): Component {
        val style = Style.style(color)
            .decoration(TextDecoration.BOLD, bold)
            .decoration(TextDecoration.ITALIC, italic)
            .decoration(TextDecoration.UNDERLINED, underlined)
            .decoration(TextDecoration.STRIKETHROUGH, strikethrough)
            .decoration(TextDecoration.OBFUSCATED, obfuscated)
        return Component.text(text).style(style)
    }
}

fun Component.toLegacyString(): String = StringExt.toLegacyString(this)
// fun Component.toLegacy(): String = LegacyComponentSerializer.legacySection().serialize(this)
fun String.toMiniMessage(): Component = StringExt.toMiniMessage(this)
fun String.toComponent(): Component = StringExt.toComponent(this.replace("&", "§"))