package io.github.grassproject.gplibrary.config

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration

fun ConfigurationSection.keysForEach(path: String, boolean: Boolean, function: (String) -> Unit) {
    val section = getConfigurationSection(path) ?: return
    section.keysForEach(boolean, function)
}

fun ConfigurationSection.keysForEach(boolean: Boolean, function: (String) -> Unit) {
    getKeys(boolean).forEach(function)
}

fun ConfigurationSection.getSectionList(path: String): List<ConfigurationSection> {
    val list = mutableListOf<ConfigurationSection>()
    val objectList = this.getList(path) ?: return list

    for (obj in objectList) {
        if (obj is ConfigurationSection) {
            list.add(obj)
        } else if (obj is Map<*, *>) {
            list.add(createConfigurationSectionFromMap(obj))
        }
    }
    return list
}

private fun createConfigurationSectionFromMap(map: Map<*, *>): ConfigurationSection {
    val mc = MemoryConfiguration()
    for ((key, value) in map) {
        when (value) {
            is Map<*, *> -> {
                mc.createSection(key.toString(), createConfigurationSectionFromMap(value).getValues(false))
            }
            is List<*> -> {
                mc[key.toString()] = value.map { item ->
                    if (item is Map<*, *>) {
                        createConfigurationSectionFromMap(item).getValues(false)
                    } else item
                }
            }
            else -> {
                mc[key.toString()] = value
            }
        }
    }
    return mc
}

fun ConfigurationSection.getOrCreateSection(path: String): ConfigurationSection {
    return this.getConfigurationSection(path) ?: this.createSection(path)
}

inline fun <reified T : Enum<T>> FileConfiguration.getEnum(path: String): T? =
    getString(path)?.uppercase()?.let { value ->
        enumValues<T>().firstOrNull { it.name == value }
    }

fun FileConfiguration.getFloat(path: String, def: Float = 0.0f): Float =
    (get(path)?.let { if (it is Number) it.toFloat() else it.toString().toFloatOrNull() } ?: def)

fun FileConfiguration.toSection(path: String): ConfigurationSection? =
    this.getConfigurationSection(path)

fun ConfigurationSection.getStringListOrEmpty(path: String): List<String> =
    this.getStringList(path).ifEmpty { emptyList() }

fun ConfigurationSection.getIntListOrEmpty(path: String): List<Int> =
    this.getIntegerList(path).ifEmpty { emptyList() }

fun ConfigurationSection.forEachSection(path: String, action: (ConfigurationSection) -> Unit) {
    val section = this.getConfigurationSection(path) ?: return
    for (key in section.getKeys(false)) {
        section.getConfigurationSection(key)?.let(action)
    }
}

inline fun <reified T : Enum<T>> FileConfiguration.getEnumList(path: String): List<T> {
    return getStringList(path).mapNotNull { raw ->
        enumValues<T>().firstOrNull { it.name.equals(raw, ignoreCase = true) }
    }
}

@Suppress("UNCHECKED_CAST")
fun ConfigurationSection.getMap(path: String): Map<String, Any> {
    return this.getConfigurationSection(path)?.getValues(false) ?: emptyMap()
}

fun YamlConfiguration.copyFrom(other: YamlConfiguration) {
    other.getKeys(true).forEach { key ->
        this.set(key, other.get(key))
    }
}
