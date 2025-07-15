package io.github.grassproject.gplibrary.util

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import java.nio.ByteBuffer
import java.util.UUID

object UUIDDataType : PersistentDataType<ByteArray, UUID> {
    override fun getPrimitiveType(): Class<ByteArray> = ByteArray::class.java
    override fun getComplexType(): Class<UUID> = UUID::class.java

    override fun toPrimitive(complex: UUID, context: PersistentDataAdapterContext): ByteArray =
        ByteBuffer.allocate(16).apply {
            putLong(complex.mostSignificantBits)
            putLong(complex.leastSignificantBits)
        }.array()

    override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext): UUID {
        val buffer = ByteBuffer.wrap(primitive)
        return UUID(buffer.long, buffer.long)
    }
}

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
