package io.github.grassproject.gplibrary.inventory

import io.github.grassproject.gplibrary.item.ItemParser
import io.github.grassproject.gplibrary.util.component.toMiniMessage
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class InventoryPagesParser(
    private val title: String,
    private val pagesLayout: List<List<String>>,
    private val pagesItemMap: List<Map<String, InventoryActionItem>>,
    private val placeholder: (String) -> String = { it }
) : GPInventory {

    private var viewer: Player? = null
    private var currentPage: Int = 0

    override fun getViewer(): Player? = viewer

    override fun getInventory(): Inventory = buildPage(currentPage)

    override fun open(player: Player) {
        viewer = player
        currentPage = 0
        player.openInventory(buildPage(currentPage))
    }

    override fun init() {}

    override fun manageClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        if (event.view.topInventory != event.inventory) return

        if (handleClick(player, currentPage, event.slot)) {
            event.isCancelled = true
        }
    }

    override fun manageClose(event: InventoryCloseEvent) {
        viewer = null
    }

    private fun buildPage(pageIndex: Int): Inventory {
        require(pageIndex in pagesLayout.indices) { "존재하지 않는 페이지입니다: $pageIndex" }

        val layout = pagesLayout[pageIndex]
        val itemMap = pagesItemMap[pageIndex]
        val inventory = Bukkit.createInventory(
            this,
            layout.size * 9,
            placeholder(title).toMiniMessage()
            // placeholder("$title - 페이지 ${pageIndex + 1}").toMiniMessage()
        )

        layout.forEachIndexed { row, line ->
            line.trim().split(" ").forEachIndexed { col, key ->
                itemMap[key]?.let {
                    inventory.setItem(row * 9 + col, it.itemStack)
                }
            }
        }

        return inventory
    }

    private fun handleClick(player: Player, pageIndex: Int, slot: Int): Boolean {
        if (pageIndex !in pagesLayout.indices) return false

        val layout = pagesLayout[pageIndex]
        val itemMap = pagesItemMap[pageIndex]

        val row = slot / 9
        val col = slot % 9
        if (row !in layout.indices) return false

        val keys = layout[row].trim().split(" ")
        if (col !in keys.indices) return false

        val key = keys[col]
        val actionItem = itemMap[key] ?: return false

        return when (actionItem.actionType) {
            InventoryActionType.COMMAND -> {
                actionItem.actionValue
                    ?.replace("%player%", player.name)
                    ?.let { player.performCommand(it); true } ?: false
            }

            InventoryActionType.OPEN_PAGE -> {
                actionItem.actionValue?.toIntOrNull()?.takeIf { it in pagesLayout.indices }?.let {
                    currentPage = it
                    player.openInventory(buildPage(it))
                    true
                } ?: false
            }

            InventoryActionType.CLOSE_INVENTORY -> {
                player.closeInventory()
                true
            }

            else -> false
        }
    }

    companion object {
        fun fromConfig(
            config: ConfigurationSection,
            placeholder: (String) -> String = { it }
        ): InventoryPagesParser {
            val title = config.getString("title") ?: ""

            val pagesSection = config.getConfigurationSection("pages")
                ?: error("pages 섹션이 존재하지 않습니다.")

            val pageKeys = pagesSection.getKeys(false)
                .sortedBy { it.toIntOrNull() ?: Int.MAX_VALUE }

            val pagesLayout = mutableListOf<List<String>>()
            val pagesItemMap = mutableListOf<Map<String, InventoryActionItem>>()

            for (pageKey in pageKeys) {
                val pageSection = pagesSection.getConfigurationSection(pageKey)
                    ?: error("페이지 '$pageKey' 섹션이 존재하지 않습니다.")

                val layout = pageSection.getStringList("layout")
                val itemsSection = pageSection.getConfigurationSection("items")
                    ?: error("페이지 '$pageKey'의 items 섹션이 존재하지 않습니다.")

                val itemMap = itemsSection.getKeys(false).associateWith { key ->
                    val itemSection = itemsSection.getConfigurationSection(key)
                        ?: error("아이템 섹션 '$key'가 존재하지 않습니다.")

                    val itemStack = ItemParser(itemSection).buildItem().build()
                    val actionSection = itemSection.getConfigurationSection("action")
                    val actionType = InventoryActionType.fromString(actionSection?.getString("type"))
                    val actionValue = actionSection?.getString("value")

                    InventoryActionItem(itemStack, actionType, actionValue)
                }

                pagesLayout.add(layout)
                pagesItemMap.add(itemMap)
            }

            return InventoryPagesParser(title, pagesLayout, pagesItemMap, placeholder)
        }
    }
}
