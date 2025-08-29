package io.github.grassproject.framework.item

import io.github.grassproject.framework.item.factory.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemHandler {

    val ITEM_FACTORIES = hashMapOf(
        "ITEMSADDER" to IAFactory,
        "CRAFTENGINE" to CraftEngineFactory,
        "NEXO" to NexoFactory
    )

    fun itemStackFromId(namespace: String): ItemStack? {
        return if (namespace.contains(":")) {
            val id = namespace.split(":").first().uppercase()
            val factory = ITEM_FACTORIES[id] ?: return null
            factory.create(namespace.substring(id.length + 1))
        } else {
            val material = Material.getMaterial(namespace.uppercase()) ?: return null
            ItemStack(material)
        }
    }

    interface Factory {
        fun create(id: String): ItemStack?
    }
}