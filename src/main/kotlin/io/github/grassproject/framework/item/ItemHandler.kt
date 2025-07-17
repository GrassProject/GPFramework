package io.github.grassproject.framework.item

import io.github.grassproject.framework.item.factory.HDBFactory
import io.github.grassproject.framework.item.factory.IAFactory
import io.github.grassproject.framework.item.factory.NexoFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemHandler {

    fun createItem(id: String): ItemStack {
        val itemType = id.substringBefore(":").lowercase()
        val itemValue = id.substringAfter(":", missingDelimiterValue = id)

        return when (itemType) {
            "hdb" -> HDBFactory.create(itemValue)
            "ia", "itemsadder" -> IAFactory.create(itemValue)
            "nexo" -> NexoFactory.create(itemValue)
            "minecraft" -> Material.matchMaterial(itemValue.uppercase())?.let { ItemStack(it) }
            else -> Material.matchMaterial(id.uppercase())?.let { ItemStack(it) }
        } ?: ItemStack(Material.STONE)

    }

    interface Factory {

        fun create(id: String): ItemStack?
    }

}