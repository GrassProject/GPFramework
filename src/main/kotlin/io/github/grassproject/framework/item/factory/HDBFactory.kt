package io.github.grassproject.framework.item.factory

import io.github.grassproject.framework.item.ItemHandler
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.inventory.ItemStack

object HDBFactory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return HeadDatabaseAPI().getItemHead(id)
    }
}