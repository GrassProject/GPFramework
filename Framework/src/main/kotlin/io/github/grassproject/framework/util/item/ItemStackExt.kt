package io.github.grassproject.framework.util.item

import org.bukkit.inventory.ItemStack

fun ItemStack.encode(): String {
    return ItemEncoder.encode(this)
}

fun String.decodeToItemStack(): ItemStack {
    return ItemEncoder.decode(this)
}