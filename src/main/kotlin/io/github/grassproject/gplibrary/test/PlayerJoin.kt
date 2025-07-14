package io.github.grassproject.gplibrary.test

import io.github.grassproject.gplibrary.item.ItemBuilder
import io.github.grassproject.gplibrary.item.ItemHandler
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.damage.DamageType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.tag.DamageTypeTags

class PlayerJoin : Listener {

    @EventHandler
    fun PlayerJoinEvent.on() {
        player.inventory.addItem(item)


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

//        val items = ItemStack(Material.PLAYER_HEAD)
//            .itemMeta as SkullMeta
//        val a = items.setOwningPlayer()
    }
}