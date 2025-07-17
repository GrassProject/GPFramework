package io.github.grassproject.framework.inventory

import org.bukkit.entity.Player

object InventoryClickHandler {
    fun handleClick(
        player: Player,
        layout: List<String>,
        itemMap: Map<String, InventoryActionItem>,
        slot: Int,
        onOpenPage: (Int) -> Unit
    ): Boolean {
        val row = slot / 9
        val col = slot % 9
        if (row !in layout.indices) return false

        val keys = layout[row].trim().split(" ")
        if (col !in keys.indices) return false

        val key = keys[col]
        val item = itemMap[key] ?: return false

        return when (item.actionType) {
            InventoryActionType.COMMAND -> {
                item.actionValue?.replace("%player%", player.name)?.let {
                    player.performCommand(it)
                    true
                } ?: false
            }
            InventoryActionType.OPEN_PAGE -> {
                val page = item.actionValue?.toIntOrNull()?.minus(1)
                if (page != null) {
                    onOpenPage(page)
                    true
                } else false
            }
            InventoryActionType.CLOSE_INVENTORY -> {
                player.closeInventory()
                true
            }
            else -> false
        }
    }
}
