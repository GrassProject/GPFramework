package io.github.grassproject.gplibrary.test

import io.github.grassproject.gplibrary.GPLibraryPlugin
import io.github.grassproject.gplibrary.api.GPItem
import io.github.grassproject.gplibrary.item.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.tag.DamageTypeTags

class PlayerJoin : Listener {

    @EventHandler
    fun PlayerJoinEvent.on() {
        player.inventory.addItem(item)
        player.sendMessage("id: ${GPItem.idFromItem(item)}")

        test(player)

    }

    companion object {
        val item = ItemBuilder(Material.DIAMOND_CHESTPLATE)
            // .setHideTooltip(true)
            .setItemName(Component.text("[GPLibrary]"))
            .setItemModel(NamespacedKey.minecraft("grass_block"))
            .setId(GPItem.ITEM_ID,"grass_test_item")
            .setDamageResistant(
                DamageTypeTags.IS_FIRE,
                DamageTypeTags.IS_EXPLOSION
            )
            .setGlider(true)
            .build()

    }

    fun test(player: Player) {
        val items = ItemStack(Material.PLAYER_HEAD)
        val meta = items.itemMeta

        val key = NamespacedKey(GPLibraryPlugin.instance, "value")
        meta.persistentDataContainer.set(key, PersistentDataType.INTEGER, 100)

        items.itemMeta = meta
        player.inventory.addItem(items)

        val value = items.itemMeta?.persistentDataContainer?.get(key, PersistentDataType.INTEGER)
        player.sendMessage(Component.text("Value is: ${value ?: "없음"}"))
    }
}