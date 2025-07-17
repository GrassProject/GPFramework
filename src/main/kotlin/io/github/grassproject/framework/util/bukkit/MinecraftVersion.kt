package io.github.grassproject.framework.util.bukkit

import org.bukkit.Bukkit
import java.util.regex.Pattern

enum class MinecraftVersion(val versionString: String) {

    V1_20("1.20.0"),
    V1_20_1("1.20.1"),
    V1_20_5("1.20.5"),
    V1_20_6("1.20.6"),
    V1_21("1.21.0"),
    V1_21_2("1.21.2"),
    V1_21_1("1.21.1"),
    V1_21_4("1.21.4");

    companion object {
        private val VERSION_PATTERN: Pattern = Pattern.compile("1\\.(19|20|21)(\\.\\d+)?")

        val rawVersion: String? by lazy {
            val bukkitVersion = Bukkit.getBukkitVersion()
            val matcher = VERSION_PATTERN.matcher(bukkitVersion)
            if (matcher.find()) matcher.group() else null
        }

        val current: MinecraftVersion? by lazy {
            val raw = rawVersion ?: return@lazy null
            entries
                .sortedByDescending { it }
                .find { it.isAtOrBelow(raw) }
        }

        fun compare(a: String, b: String): Int {
            val parts1 = a.split(".", "-").mapNotNull { it.toIntOrNull() }
            val parts2 = b.split(".", "-").mapNotNull { it.toIntOrNull() }
            val length = maxOf(parts1.size, parts2.size)

            for (i in 0 until length) {
                val p1 = parts1.getOrElse(i) { 0 }
                val p2 = parts2.getOrElse(i) { 0 }
                if (p1 != p2) return p1 - p2
            }
            return 0
        }
    }

    fun isExact(): Boolean = versionString == rawVersion
    fun isAtOrAbove(): Boolean = rawVersion?.let { compare(it, versionString) >= 0 } ?: false
    fun isAtOrBelow(): Boolean = rawVersion?.let { compare(it, versionString) <= 0 } ?: false
    fun isAbove(): Boolean = rawVersion?.let { compare(it, versionString) > 0 } ?: false
    fun isBelow(): Boolean = rawVersion?.let { compare(it, versionString) < 0 } ?: false

    fun isAtOrAbove(other: String): Boolean = compare(versionString, other) >= 0
    fun isAtOrBelow(other: String): Boolean = compare(versionString, other) <= 0
}