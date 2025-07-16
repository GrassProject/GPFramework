package io.github.grassproject.gplibrary.inventory

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

interface GPInventory : InventoryHolder {
    fun manageClick(event: InventoryClickEvent)
    fun manageClose(event: InventoryCloseEvent)
    fun init()
    fun getViewer(): Player?
    fun open(player: Player)
    override fun getInventory(): Inventory
}