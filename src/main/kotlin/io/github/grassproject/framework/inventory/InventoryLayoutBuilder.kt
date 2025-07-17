package io.github.grassproject.framework.inventory

import io.github.grassproject.framework.util.component.toMiniMessage
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

object InventoryLayoutBuilder {
    fun build(
        holder: InventoryHolder?,
        title: String,
        layout: List<String>,
        itemMap: Map<String, InventoryActionItem>,
        placeholder: (String) -> String = { it }
    ): Inventory {
        val inventory = Bukkit.createInventory(holder, layout.size * 9, placeholder(title).toMiniMessage())

        layout.forEachIndexed { row, line ->
            line.trim().split(" ").forEachIndexed { col, key ->
                itemMap[key]?.let {
                    inventory.setItem(row * 9 + col, it.itemStack)
                }
            }
        }

        return inventory
    }
}
