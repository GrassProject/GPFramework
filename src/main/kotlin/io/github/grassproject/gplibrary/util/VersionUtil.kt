package io.github.grassproject.gplibrary.util

import org.bukkit.Bukkit
import java.util.regex.Pattern

object VersionUtil {

    private val VERSION_PATTERN: Pattern = Pattern.compile("1\\.(19|20|21)(\\.\\d+)?")

    val version: String?
        get() = getBukkitVersion(Bukkit.getBukkitVersion())

    fun getBukkitVersion(version: String): String? {
        val matcher = VERSION_PATTERN.matcher(version)
        return if (matcher.find()) matcher.group() else null
    }

    val isVersion_1_21: Boolean
        get() {
            val version = version
            return version != null && version.matches("1\\.(21|21(\\.\\d+)?)".toRegex())
        }

    val isVersion_1_20_5: Boolean
        get() {
            val version = version
            return version != null && version.matches("1\\.(20\\.5|20\\.6(\\.\\d+)?)".toRegex())
        }

    val isVersion_1_20: Boolean
        get() {
            val version = version
            return version != null && version.matches("1\\.(19\\.4|20(\\.\\d+)?)".toRegex())
        }

    val isVersion_1_19_4: Boolean
        get() {
            val version = version
            return "1.19.4" == version
        }

    fun isVersionLowerThan(target: String): Boolean {
        val current = version ?: return true
        return compareVersion(current, target) < 0
    }

    fun isVersionGreaterThan(target: String): Boolean {
        val current = version ?: return false
        return compareVersion(current, target) > 0
    }

    fun isVersionAtOrAbove(target: String): Boolean {
        val current = version ?: return false
        return compareVersion(current, target) >= 0
    }

    fun isVersionAtLeast(target: String): Boolean {
        val current = version ?: return false
        return compareVersion(current, target) >= 0
    }

    private fun compareVersion(version1: String, version2: String): Int {
        val parts1 = version1.split(".", "-").mapNotNull { it.toIntOrNull() }
        val parts2 = version2.split(".", "-").mapNotNull { it.toIntOrNull() }
        val length = maxOf(parts1.size, parts2.size)

        for (i in 0 until length) {
            val p1 = parts1.getOrElse(i) { 0 }
            val p2 = parts2.getOrElse(i) { 0 }
            if (p1 != p2) return p1 - p2
        }
        return 0
    }
}