package io.github.grassproject.gplibrary.item.factory

import com.nexomc.nexo.api.NexoItems
import io.github.grassproject.gplibrary.item.ItemHandler
import org.bukkit.inventory.ItemStack

object NexoFactory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return NexoItems.itemFromId(id)?.build()
    }

}