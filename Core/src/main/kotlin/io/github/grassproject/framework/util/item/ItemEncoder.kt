package io.github.grassproject.framework.util.item

import org.bukkit.inventory.ItemStack
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder

object ItemEncoder {

    @JvmStatic
    fun encode(itemStack: ItemStack): String {
        return String(Base64Coder.encode(itemStack.serializeAsBytes()))
    }

    @JvmStatic
    fun decode(base64: String): ItemStack {
        return ItemStack.deserializeBytes(Base64Coder.decode(base64))
    }

    @JvmStatic
    fun encodeItems(items: List<ItemStack>): String {
        return String(ItemStack.serializeItemsAsBytes(items), Charsets.UTF_8)
    }

    @JvmStatic
    fun decodeItems(base64: String): List<ItemStack> {
        return ItemStack.deserializeItemsFromBytes(Base64Coder.decode(base64)).toList()
    }

}