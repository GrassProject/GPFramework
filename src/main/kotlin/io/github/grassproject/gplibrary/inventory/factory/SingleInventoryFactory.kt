package io.github.grassproject.gplibrary.inventory.factory

import io.github.grassproject.gplibrary.inventory.IGPInventory
import io.github.grassproject.gplibrary.inventory.InventoryActionItem
import io.github.grassproject.gplibrary.inventory.InventoryLayoutBuilder
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class SingleInventoryFactory(
    private val title: String,
    private val layout: List<String>,
    private val itemMap: Map<String, InventoryActionItem>,
    private val placeholder: (String) -> String = { it }
) : IGPInventory {

    private var viewer: Player? = null
    private val inventory: Inventory by lazy {
        InventoryLayoutBuilder.build(this, title, layout, itemMap, placeholder)
    }

    override fun getViewer(): Player? = viewer

    override fun getInventory(): Inventory = inventory

    override fun open(player: Player) {
        viewer = player
        player.openInventory(inventory)
    }

    override fun init() {}

    override fun manageClick(event: InventoryClickEvent) {}

    override fun manageClose(event: InventoryCloseEvent) {
        viewer = null
    }
}