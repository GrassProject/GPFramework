package io.github.grassproject.gplibrary.api

import io.github.grassproject.gplibrary.GPLibraryPlugin
import io.github.grassproject.gplibrary.item.ItemBuilder
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

// 차후 다른 플러그인에서 쓰는 코드
// ex) Nexo 아이템 구분용
object GPItem {

    val ITEM_ID = NamespacedKey(GPLibraryPlugin.instance, "id")

    fun idFromItem(
        item: ItemBuilder,
        namespacedKey: NamespacedKey = ITEM_ID
    ): String? = item.getPDC(namespacedKey, PersistentDataType.STRING)

    fun idFromItem(
        item: ItemStack,
        namespacedKey: NamespacedKey = ITEM_ID
    ): String? = item.itemMeta.persistentDataContainer
        .takeIf { !it.isEmpty }
        ?.get(namespacedKey, PersistentDataType.STRING)

}