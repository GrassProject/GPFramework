package io.github.grassproject.gplibrary.util.pdc.data

import org.bukkit.persistence.PersistentDataAdapterContext
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