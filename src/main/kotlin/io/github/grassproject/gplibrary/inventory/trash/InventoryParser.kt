package io.github.grassproject.gplibrary.inventory.trash

import io.github.grassproject.gplibrary.item.ItemParser
import io.github.grassproject.gplibrary.util.component.toMiniMessage
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class InventoryParser (
    private val holder: InventoryHolder?,
    private val title: String,
    private val layout: List<String>,
    private val itemMap: Map<String, ItemStack>,
    private val placeholder: (String) -> String = { it }
) {

    fun build(): Inventory {
        val rows = layout.size
        val inventory = Bukkit.createInventory(holder, rows * 9, placeholder(title).toMiniMessage())

        layout.forEachIndexed { rowIndex, line ->
            val keys = line.trim().split(" ")
            keys.forEachIndexed { colIndex, key ->
                val item = itemMap[key]
                if (item != null) {
                    val slot = rowIndex * 9 + colIndex
                    inventory.setItem(slot, item)
                }
            }
        }
        return inventory
    }

    companion object {
        fun fromConfig(
            holder: InventoryHolder?,
            config: ConfigurationSection,
            placeholder: (String) -> String = { it }
        ): InventoryParser {
            val title = config.getString("title") ?: ""
            val layout = config.getStringList("layout")
            val itemsSection = config.getConfigurationSection("items")
                ?: throw IllegalArgumentException("items 섹션이 존재하지 않습니다.")

            val itemMap = itemsSection.getKeys(false).associateWith { key ->
                val section = itemsSection.getConfigurationSection(key)
                    ?: throw IllegalArgumentException("아이템 섹션 '$key'이(가) 존재하지 않습니다.")
                ItemParser(section).buildItem().build()
            }

            return InventoryParser(holder, title, layout, itemMap, placeholder)
        }
    }
}