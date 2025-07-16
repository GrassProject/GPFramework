package io.github.grassproject.gplibrary.listener

import io.github.grassproject.gplibrary.inventory.GPInventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoryListener : Listener {

    @EventHandler
    fun InventoryClickEvent.onClick() {
        val inventory = clickedInventory ?: return
        val holder = inventory.holder

        if (holder is GPInventory) {
            holder.manageClick(this)
        }
    }

    @EventHandler
    fun InventoryCloseEvent.onClose() {
        val holder = inventory.holder

        if (holder is GPInventory) {
            holder.manageClose(this)
        }
    }
}