package io.github.grassproject.gplibrary.inventory.trash

import org.bukkit.inventory.ItemStack

enum class InventoryActionType {
    NONE, COMMAND, OPEN_PAGE, CLOSE_INVENTORY;

    companion object {
        fun fromString(value: String?): InventoryActionType {
            return when (value?.lowercase()) {
                "command" -> COMMAND
                "open_page" -> OPEN_PAGE
                "close_inventory" -> CLOSE_INVENTORY
                else -> NONE
            }
        }
    }
}

data class InventoryActionItem(
    val itemStack: ItemStack,
    val actionType: InventoryActionType = InventoryActionType.NONE,
    val actionValue: String? = null
)
