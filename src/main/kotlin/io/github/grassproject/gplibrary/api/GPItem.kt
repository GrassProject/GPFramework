package io.github.grassproject.gplibrary.api

import io.github.grassproject.gplibrary.GPLibraryPlugin
import io.github.grassproject.gplibrary.item.ItemBuilder
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


object GPItem {

    val ITEM_ID = NamespacedKey(GPLibraryPlugin.instance, "id")

    fun getIdByItem(
        namespacedKey: NamespacedKey = ITEM_ID,
        item: ItemBuilder
    ): String? {
        return item.getPDC(namespacedKey, PersistentDataType.STRING)
    }

//    fun getIdByItem(
//        namespacedKey: NamespacedKey = ITEM_ID,
//        item: ItemStack
//    ): String? {
//        return (if (item.itemMeta == null || item.itemMeta.persistentDataContainer.isEmpty) null
//        else
//            item.itemMeta[namespacedKey, PersistentDataType.STRING]) as String?
//    }

}