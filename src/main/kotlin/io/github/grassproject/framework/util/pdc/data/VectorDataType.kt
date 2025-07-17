package io.github.grassproject.framework.util.pdc.data

import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Vector
import java.nio.ByteBuffer

object VectorDataType : PersistentDataType<ByteArray, Vector> {
    override fun getPrimitiveType() = ByteArray::class.java
    override fun getComplexType() = Vector::class.java

    override fun toPrimitive(complex: Vector, context: PersistentDataAdapterContext): ByteArray {
        val buffer = ByteBuffer.allocate(24)
        buffer.putDouble(complex.x)
        buffer.putDouble(complex.y)
        buffer.putDouble(complex.z)
        return buffer.array()
    }

    override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext): Vector {
        val buffer = ByteBuffer.wrap(primitive)
        val x = buffer.double
        val y = buffer.double
        val z = buffer.double
        return Vector(x, y, z)
    }
}
