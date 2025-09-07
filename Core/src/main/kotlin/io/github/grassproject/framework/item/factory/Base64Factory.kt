package io.github.grassproject.framework.item.factory

import io.github.grassproject.framework.item.ItemHandler
import io.github.grassproject.framework.util.item.ItemEncoder
import org.bukkit.inventory.ItemStack

object Base64Factory : ItemHandler.Factory {
    override fun create(id: String): ItemStack {
        return ItemEncoder.decode(id)
    }
}