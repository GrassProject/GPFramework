package io.github.grassproject.framework.item.factory

import dev.lone.itemsadder.api.CustomStack
import io.github.grassproject.framework.item.ItemHandler
import org.bukkit.inventory.ItemStack

object IAFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return CustomStack.getInstance(id)!!.itemStack
    }
}