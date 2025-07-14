package io.github.grassproject.gplibrary.item

import io.github.grassproject.gplibrary.item.factory.IAFactory
import io.github.grassproject.gplibrary.item.factory.NexoFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemHandler {

    fun createItem(id: String): ItemStack? {
        val itemType = id.substringBefore(":").lowercase()
        val itemValue = id.substringAfter(":", missingDelimiterValue = id)

        return when (itemType) {
            "ia", "itemsadder" -> IAFactory.create(itemValue)
            "nexo" -> NexoFactory.create(itemValue)
            "minecraft" -> Material.matchMaterial(itemValue.uppercase())?.let { ItemStack(it) } ?: ItemStack(Material.AIR)
            else -> Material.matchMaterial(id.uppercase())?.let { ItemStack(it) } ?: ItemStack(Material.AIR)
        } ?: ItemStack(Material.AIR)

    }

    interface Factory {

        fun create(id: String): ItemStack?
    }

}