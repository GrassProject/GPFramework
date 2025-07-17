package io.github.grassproject.gplibrary.item

import io.github.grassproject.gplibrary.GPLibraryPlugin
import io.github.grassproject.gplibrary.util.pdc.get
import io.github.grassproject.gplibrary.util.pdc.set
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import org.bukkit.damage.DamageType
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.components.EquippableComponent
import org.bukkit.inventory.meta.components.FoodComponent
import org.bukkit.inventory.meta.components.ToolComponent
import org.bukkit.inventory.meta.components.UseCooldownComponent
import org.bukkit.persistence.PersistentDataType


class ItemBuilder(var itemStack: ItemStack) {

    private var itemMeta: ItemMeta = itemStack.itemMeta

    constructor(material: Material) : this(ItemStack(material))

    constructor(id: String) : this(ItemHandler.createItem(id))

    fun setId(namespacedKey: NamespacedKey, id: String): ItemBuilder {
        itemMeta[namespacedKey, PersistentDataType.STRING] = id
        return this
    }

    fun <T : Any, Z : Any> setPDC(
        namespacedKey: NamespacedKey,
        persistentDataType: PersistentDataType<T, Z>,
        value: Z
    ): ItemBuilder {
        itemMeta[namespacedKey, persistentDataType] = value
        return this
    }

    fun <T : Any, Z : Any> getPDC(
        namespacedKey: NamespacedKey,
        persistentDataType: PersistentDataType<T, Z>
    ): Z? = itemMeta[namespacedKey, persistentDataType]

//    fun modifierMeta(modifier: Consumer<ItemMeta>): ItemBuilder {
//        modifier.accept(itemMeta)
//        return this
//    }

    fun setType(type: Material): ItemBuilder {
        itemStack = ItemStack(type)
        return this
    }

    fun setType(type: ItemStack): ItemBuilder {
        itemStack = type.clone()
        return this
    }

    fun setAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    fun setItemName(itemName: Component): ItemBuilder {
        val styledName = itemName.decorationIfAbsent(
            TextDecoration.ITALIC,
            TextDecoration.State.FALSE
        ).colorIfAbsent(NamedTextColor.WHITE)
        itemMeta.itemName(styledName)
        return this
    }

    fun setLore(lore: List<Component>): ItemBuilder {
        val styledLore = lore.map { it.decorationIfAbsent(
                TextDecoration.ITALIC, TextDecoration.State.FALSE
            ) }
        itemMeta.lore(styledLore)
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): ItemBuilder {
        itemMeta.isUnbreakable = unbreakable
        return this
    }

    fun addItemFlags(vararg itemFlags: ItemFlag): ItemBuilder {
        itemMeta.addItemFlags(*itemFlags)
        return this
    }

    fun addEnchant(enchant: Enchantment, level: Int): ItemBuilder {
        itemMeta.addEnchant(enchant, level, true)
        return this
    }

    fun setRarity(rarity: ItemRarity): ItemBuilder {
        itemMeta.setRarity(rarity)
        return this
    }

    // 커모델은 차후 지정

    // 1_21_5 +
    fun setHideTooltip(hideTooltip: Boolean): ItemBuilder {
        itemMeta.isHideTooltip = hideTooltip
        return this
    }

    fun setUnstackable(unstackable: Boolean): ItemBuilder {
        if (unstackable) itemMeta.setMaxStackSize(1)
        return this
    }

    fun setMaxStackSize(size: Int): ItemBuilder {
        itemMeta.setMaxStackSize(size)
        return this
    }

    fun setFoodComponent(foodComponent: FoodComponent): ItemBuilder {
        itemMeta.setFood(foodComponent)
        return this
    }

    fun setToolComponent(toolComponent: ToolComponent): ItemBuilder {
        itemMeta.setTool(toolComponent)
        return this
    }

    fun setDurability(durability: Int): ItemBuilder {
        if (itemMeta is Damageable) (itemMeta as Damageable).damage = durability
        return this
    }

    fun setDamagedOnBlockBreak(damagedOnBlockBreak: Boolean): ItemBuilder {
        val container = itemMeta.persistentDataContainer
        val key = NamespacedKey(GPLibraryPlugin.instance, "damagedOnBlockBreak")
        container.set(key, PersistentDataType.BYTE, if (damagedOnBlockBreak) 1.toByte() else 0.toByte())
        return this
    }

    fun setDamagedOnEntityHit(damagedOnEntityHit: Boolean): ItemBuilder {
        val container = itemMeta.persistentDataContainer
        val key = NamespacedKey(GPLibraryPlugin.instance, "damagedOnEntityHit")
        container.set(key, PersistentDataType.BYTE, if (damagedOnEntityHit) 1.toByte() else 0.toByte())
        return this
    }

    // 1_21_2 +
    fun setUseCooldownComponent(useCooldownComponent: UseCooldownComponent): ItemBuilder {
        itemMeta.setUseCooldown(useCooldownComponent)
        return this
    }

    fun setTooltipStyle(tooltipStyle: NamespacedKey): ItemBuilder {
        itemMeta.tooltipStyle = tooltipStyle
        return this
    }

    fun setItemModel(itemModel: NamespacedKey): ItemBuilder {
        itemMeta.itemModel = itemModel
        return this
    }

    fun setEnchantable(enchantable: Int): ItemBuilder {
        itemMeta.setEnchantable(enchantable)
        return this
    }

    // @Deprecated("임시")
    fun setEquippable(equippableComponent: EquippableComponent): ItemBuilder {
        itemMeta.setEquippable(equippableComponent)
        return this
    }

    fun setDamageResistant(vararg damageResistant: Tag<DamageType>): ItemBuilder {
        damageResistant.forEach { itemMeta.damageResistant = it }
        return this
    }

//    fun addDamageResistant(vararg damageResistant: Tag<DamageType>): ItemBuilder {
//        val current = itemMeta.damageResistant?.toMutableSet() ?: mutableSetOf()
//        current.addAll(damageResistant)
//        itemMeta.damageResistant = current
//        return this
//    }

    fun setGlider(glider: Boolean): ItemBuilder {
        itemMeta.isGlider = glider
        return this
    }

    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}