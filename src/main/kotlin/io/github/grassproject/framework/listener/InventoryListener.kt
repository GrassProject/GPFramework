package io.github.grassproject.framework.listener

import io.github.grassproject.framework.trash.inventory.trash.IGPInventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoryListener : Listener {

    @EventHandler
    fun InventoryClickEvent.onClick() {
        val holder = inventory.holder as? IGPInventory ?: return
        holder.manageClick(this)
    }

    @EventHandler
    fun InventoryCloseEvent.onClose() {
        val holder = inventory.holder as? IGPInventory ?: return
        holder.manageClose(this)
    }
}
