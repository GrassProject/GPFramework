package io.github.grassproject.framework.inventory

import org.bukkit.inventory.ItemStack

data class InventoryActionItem(
    val itemStack: ItemStack,
    val actionType: InventoryActionType = InventoryActionType.NONE,
    val actionValue: String? = null
)
