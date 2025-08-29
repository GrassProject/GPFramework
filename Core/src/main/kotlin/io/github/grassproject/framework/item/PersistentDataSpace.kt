package io.github.grassproject.framework.item

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType

data class PersistentDataSpace<T, Z>(val key: NamespacedKey, val dataType: PersistentDataType<T, Z>)