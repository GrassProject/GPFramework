package io.github.grassproject.framework.trash.inventory

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class GPInventory(
    title: Component,
    size: Int = 27,
    type: InventoryType? = null,
) : InventoryHolder {

    private val inventory = if (type != null) Bukkit.createInventory(this, type, title)
    else Bukkit.createInventory(this, size, title)

    override fun getInventory(): Inventory = inventory

    fun open(player: Player) = player.openInventory(inventory)

    abstract fun onOpen(event: InventoryOpenEvent)
    abstract fun onClose(event: InventoryCloseEvent)
    abstract fun onClick(event: InventoryClickEvent)
    abstract fun onDrag(event: InventoryDragEvent)
}