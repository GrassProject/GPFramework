package io.github.grassproject.framework.inventory.factory

import io.github.grassproject.framework.inventory.IGPInventory
import io.github.grassproject.framework.inventory.InventoryActionItem
import io.github.grassproject.framework.inventory.InventoryClickHandler
import io.github.grassproject.framework.inventory.InventoryLayoutBuilder
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class PagedInventoryFactory(
    private val title: String,
    private val layouts: List<List<String>>,
    private val itemMaps: List<Map<String, InventoryActionItem>>,
    private val placeholder: (String) -> String = { it }
) : IGPInventory {

    private var viewer: Player? = null
    private var currentPage: Int = 0

    override fun getViewer(): Player? = viewer

    override fun getInventory(): Inventory = buildInventory(currentPage)

    override fun open(player: Player) {
        viewer = player
        currentPage = 0
        player.openInventory(getInventory())
    }

    override fun init() {}

    override fun manageClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        if (event.view.topInventory != event.inventory) return

        val layout = layouts[currentPage]
        val itemMap = itemMaps[currentPage]
        val handled = InventoryClickHandler.handleClick(player, layout, itemMap, event.slot) { pageIndex ->
            if (pageIndex in layouts.indices) {
                currentPage = pageIndex
                player.openInventory(getInventory())
            }
        }

        if (handled) event.isCancelled = true
    }

    override fun manageClose(event: InventoryCloseEvent) {
        viewer = null
    }

    private fun buildInventory(pageIndex: Int): Inventory {
        return InventoryLayoutBuilder.build(this, title, layouts[pageIndex], itemMaps[pageIndex], placeholder)
    }
}