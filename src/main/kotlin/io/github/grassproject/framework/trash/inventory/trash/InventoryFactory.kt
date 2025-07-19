package io.github.grassproject.framework.trash.inventory.trash

import io.github.grassproject.framework.item.ItemParser
import io.github.grassproject.framework.trash.inventory.trash.factory.PagedInventoryFactory
import io.github.grassproject.framework.trash.inventory.trash.factory.SingleInventoryFactory
import org.bukkit.configuration.ConfigurationSection

object InventoryFactory {

    fun fromConfig(
        config: ConfigurationSection,
        placeholder: (String) -> String = { it },
        // holder: InventoryHolder? = null
    ): IGPInventory {
        val title = config.getString("title") ?: ""

        config.getConfigurationSection("pages")?.let { pagesSection ->
            val layouts = mutableListOf<List<String>>()
            val itemMaps = mutableListOf<Map<String, InventoryActionItem>>()

            pagesSection.getKeys(false)
                .sortedBy { it.toIntOrNull() ?: Int.MAX_VALUE }
                .forEach { key ->
                    val pageSection = pagesSection.getConfigurationSection(key)
                        ?: error("페이지 '$key' 섹션이 없습니다.")
                    layouts += pageSection.getStringList("layout")
                    itemMaps += parseItemMap(pageSection.getConfigurationSection("items"), true)
                }

            return PagedInventoryFactory(title, layouts, itemMaps, placeholder)
        }

        val layout = config.getStringList("layout")
        val itemMap = parseItemMap(config.getConfigurationSection("items"), false)
        return SingleInventoryFactory(title, layout, itemMap, placeholder)
    }

    private fun parseItemMap(
        section: ConfigurationSection?,
        parseAction: Boolean
    ): Map<String, InventoryActionItem> {
        requireNotNull(section) { "items 섹션이 없습니다." }

        return section.getKeys(false).associateWith { key ->
            val itemSection = section.getConfigurationSection(key)
                ?: error("아이템 섹션 '$key'가 없습니다.")
            val stack = ItemParser(itemSection).buildItem().build()

            val actionSection = itemSection.getConfigurationSection("action")
            val actionType = if (parseAction) {
                InventoryActionType.fromString(actionSection?.getString("type"))
            } else InventoryActionType.NONE
            val actionValue = if (parseAction) actionSection?.getString("value") else null

            InventoryActionItem(stack, actionType, actionValue)
        }
    }
}