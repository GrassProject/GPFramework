package io.github.grassproject.framework.util.pdc

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType

operator fun <T : Any, Z : Any> PersistentDataHolder.get(
    key: NamespacedKey,
    type: PersistentDataType<T, Z>
): Z? = persistentDataContainer.takeIf { it.has(key, type) }?.get(key, type)

operator fun <T : Any, Z : Any> PersistentDataHolder.set(
    key: NamespacedKey,
    type: PersistentDataType<T, Z>,
    value: Z?
) {
    val container = persistentDataContainer
    if (value == null) container.remove(key)
    else container.set(key, type, value)
}

fun PersistentDataHolder.remove(key: NamespacedKey) {
    persistentDataContainer.remove(key)
}
