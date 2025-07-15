package io.github.grassproject.gplibrary.test

import io.github.grassproject.gplibrary.item.ItemBuilder
import io.github.grassproject.gplibrary.util.get
import io.github.grassproject.gplibrary.util.set
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
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
        items[key, PersistentDataType.INTEGER] = 100
        player.sendMessage(
            Component.text("Value is: ${items[key, PersistentDataType.INTEGER] ?: "없음"}")
        )

    }

    companion object {
        val item = ItemBuilder(Material.DIAMOND_CHESTPLATE)
            // .setHideTooltip(true)
            .setItemName(Component.text("[GPLibrary]"))
            .setItemModel(NamespacedKey.minecraft("grass_block"))
            .setNamespacedKey(ItemBuilder.ID_KEY,"grass_test_item")
            .setDamageResistant(
                DamageTypeTags.IS_FIRE,
                DamageTypeTags.IS_EXPLOSION
            )
            .setGlider(true)
            .build()

        val key = NamespacedKey.minecraft("some_key")
        val items = ItemStack(Material.PLAYER_HEAD).itemMeta
    }
}