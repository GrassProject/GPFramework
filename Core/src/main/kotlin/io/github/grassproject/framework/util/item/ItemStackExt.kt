package io.github.grassproject.framework.util.item

import io.github.grassproject.framework.item.ItemBuilder
import org.bukkit.inventory.ItemStack

fun ItemStack.encode(): String {
    return ItemEncoder.encode(this)
}

fun String.decodeToItemStack(): ItemStack {
    return ItemEncoder.decode(this)
}

fun ItemStack.toItemBuilder(): ItemBuilder = ItemBuilder(this).clone()