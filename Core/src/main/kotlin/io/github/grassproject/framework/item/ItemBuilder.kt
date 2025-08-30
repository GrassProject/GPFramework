package io.github.grassproject.framework.item

import com.google.common.collect.Multimap
import io.github.grassproject.framework.util.bukkit.MinecraftVersion
import io.github.grassproject.framework.util.component.toDefaultStyle
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.components.CustomModelDataComponent
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

class ItemBuilder(private val itemStack: ItemStack) {

    constructor(material: Material) : this(ItemStack(material))
    constructor(namespace: String) : this(ItemHandler.itemStackFromId(namespace) ?: ItemStack(Material.PAPER))

    val persistentDataMap: MutableMap<PersistentDataSpace<*, *>, Any> = mutableMapOf()
    val persistentDataContainer: PersistentDataContainer
    val enchantments: MutableMap<Enchantment, Int>
    var amount: Int
    var displayName: Component? = null
    var unbreakable: Boolean
    var itemFlags: MutableSet<ItemFlag> = mutableSetOf()
    var attributeModifiers: Multimap<Attribute, AttributeModifier>? = null
    var customModelData: Int? = null
    var lore: List<Component>? = null
    var finalItemStack: ItemStack? = null

    // 1.20.5+
    var itemName: Component? = null
    var hideTooltip: Boolean? = null
    var maxStackSize: Int? = null

    // 1.21.2+
    var tooltipStyle: NamespacedKey? = null
    var itemModel: NamespacedKey? = null
    var isGlider: Boolean? = null
    var enchantable: Int? = null

    // 1.21.4+
    var customModelDataComponent: CustomModelDataComponent? = null

    init {
        amount = itemStack.amount

        val itemMeta: ItemMeta = checkNotNull(itemStack.itemMeta)

        displayName = itemMeta.displayName()
        lore = itemMeta.lore()

        unbreakable = itemMeta.isUnbreakable

        if (itemMeta.itemFlags.isNotEmpty()) itemFlags = itemMeta.itemFlags

        attributeModifiers = itemMeta.attributeModifiers

        customModelData = if (itemMeta.hasCustomModelData()) itemMeta.customModelData else null

        persistentDataContainer = itemMeta.persistentDataContainer

        enchantments = HashMap()

        if (MinecraftVersion.V1_20_5.isAtOrAbove()) {
            itemName = if (itemMeta.hasItemName()) itemMeta.itemName() else null
            hideTooltip = if (itemMeta.isHideTooltip) true else null
            maxStackSize = if (itemMeta.hasMaxStackSize()) itemMeta.maxStackSize else null
        }

        if (MinecraftVersion.V1_21_2.isAtOrAbove()) {
            itemModel = if (itemMeta.hasItemModel()) itemMeta.itemModel else null
            enchantable = if (itemMeta.hasEnchantable()) itemMeta.enchantable else null
            isGlider = if (itemMeta.isGlider) true else null
        }

        if (MinecraftVersion.V1_21_4.isAtOrAbove()) {
            customModelDataComponent = if (itemMeta.hasCustomModelData()) itemMeta.customModelDataComponent else null
        }
    }

    fun hasPDC(): Boolean = !persistentDataContainer.isEmpty

    fun <T, Z> setPDC(
        namespacedKey: NamespacedKey,
        dataType: PersistentDataType<T, Z>,
        data: Z
    ) = apply {
        persistentDataMap[PersistentDataSpace(namespacedKey, dataType)] = data as Any
    }

    @Suppress("UNCHECKED_CAST")
    fun <T, Z> getPDC(namespacedKey: NamespacedKey, dataType: PersistentDataType<T, Z>): Z? {
        for ((key, value) in persistentDataMap) {
            if (key.key == namespacedKey && key.dataType == dataType)
                return value as Z
        }
        return null
    }

    fun removePDC(key: NamespacedKey) = apply { persistentDataContainer.remove(key) }

    fun amount(amount: Int) = apply { this.amount = amount }
    fun displayName(displayName: Component?) = apply { this.displayName = displayName }
    fun unbreakable(unbreakable: Boolean) = apply { this.unbreakable = unbreakable }
    fun itemFlags(itemFlags: Set<ItemFlag>) = apply { this.itemFlags = itemFlags.toMutableSet() }
    fun attributeModifiers(attributeModifiers: Multimap<Attribute, AttributeModifier>?) = apply { this.attributeModifiers = attributeModifiers }
    fun customModelData(customModelData: Int?) = apply { this.customModelData = customModelData }
    fun lore(lore: List<Component>?) = apply { this.lore = lore }
    fun finalItemStack(finalItemStack: ItemStack?) = apply { this.finalItemStack = finalItemStack }
    fun addEnchant(enchant: Enchantment, level: Int) = apply { enchantments[enchant] = level }
    fun addEnchants(enchants: Map<Enchantment, Int>) = apply { enchants.forEach(::addEnchant) }

    // 1.20.5+
    fun itemName(itemName: Component?) = apply { this.itemName = itemName }
    fun hideTooltip(hideTooltip: Boolean?) = apply { this.hideTooltip = hideTooltip }
    fun maxStackSize(maxStackSize: Int?) = apply { this.maxStackSize = maxStackSize }

    // 1.21.2+
    fun tooltipStyle(tooltipStyle: NamespacedKey?) = apply { this.tooltipStyle = tooltipStyle }
    fun isGlider(isGlider: Boolean?) = apply { this.isGlider = isGlider }
    fun itemModel(itemModel: NamespacedKey?) = apply { this.itemModel = itemModel }
    fun enchantable(enchantable: Int?) = apply { this.enchantable = enchantable }

    fun customModelDataComponent(customModelDataComponent: CustomModelDataComponent?) = apply { this.customModelDataComponent = customModelDataComponent }

    fun referenceCopy() = itemStack.clone()

    fun clone() = ItemBuilder(build())

    fun regenerateItem() {
        if (amount != itemStack.amount) itemStack.amount = amount

        val itemMeta = itemStack.itemMeta

        if (MinecraftVersion.V1_20_5.isAtOrAbove()) {
            if (itemName != null) itemMeta.itemName(itemName)
            if (hideTooltip != null) itemMeta.isHideTooltip = hideTooltip!!
            if (maxStackSize != null) itemMeta.setMaxStackSize(maxStackSize)
        }

        if (MinecraftVersion.V1_21_2.isAtOrAbove()) {
            if (tooltipStyle != null) itemMeta.tooltipStyle = tooltipStyle
            if (isGlider != null) itemMeta.isGlider = isGlider!!
            if (itemModel != null) itemMeta.itemModel = itemModel
            if (enchantable != null) itemMeta.setEnchantable(enchantable)
        }

        itemMeta.isUnbreakable = unbreakable

        val pdc = itemMeta.persistentDataContainer
        displayName?.let { name ->
            itemMeta.displayName(name.toDefaultStyle())
        }

        enchantments.entries.forEach { enchant: Map.Entry<Enchantment?, Int?> ->
            if (enchant.key == null) return@forEach
            val lvl = enchant.value ?: 1
            itemMeta.addEnchant(enchant.key!!, lvl, true)
        }

        itemMeta.addItemFlags(*itemFlags.toTypedArray())
        attributeModifiers?.also(itemMeta::setAttributeModifiers)
        itemMeta.setCustomModelData(customModelData)

        if (MinecraftVersion.V1_21_4.isAtOrAbove()) {
            val cmdComponent = (customModelDataComponent ?: itemMeta.customModelDataComponent.takeIf { customModelData != null })?.apply {
                if (customModelData != null) floats = floats.plus(customModelData!!.toFloat()).distinct()
            }
            itemMeta.setCustomModelDataComponent(cmdComponent)
        }

        @Suppress("UNCHECKED_CAST")
        for ((key, value) in persistentDataMap) {
            (key as? PersistentDataSpace<Any, Any>)?.let {
                pdc.set(it.key, it.dataType, value)
            }
        }

        itemMeta.lore(lore.toDefaultStyle())

        itemStack.itemMeta = itemMeta
        finalItemStack = itemStack
    }

    fun build(): ItemStack {
        if (finalItemStack == null) regenerateItem()
        return finalItemStack!!.clone()
    }
}