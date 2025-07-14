package io.github.grassproject.gplibrary.item.factory

import io.github.grassproject.gplibrary.item.ItemHandler
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.inventory.ItemStack

object HDBFactory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return HeadDatabaseAPI().getItemHead(id)
    }
}