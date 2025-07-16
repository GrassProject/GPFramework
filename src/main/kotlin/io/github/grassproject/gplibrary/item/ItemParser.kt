package io.github.grassproject.gplibrary.item

import io.github.grassproject.gplibrary.item.util.DamageTypeTagRegistry
import io.github.grassproject.gplibrary.util.VersionUtil
import io.github.grassproject.gplibrary.util.toMiniMessage
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.damage.DamageType
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import org.bukkit.tag.DamageTypeTags

class ItemParser(private val section: ConfigurationSection) {

    private val type: Material = run {
        val materialName = section.getString("material") ?: "STONE"
        ItemHandler.createItem(materialName).type
    }

    fun buildItem(): ItemBuilder {
        val item = ItemBuilder(type)
        return applyConfig(item)
    }

    fun applyConfig(item: ItemBuilder): ItemBuilder {
        val materialName = section.getString("material") ?: "STONE"
//        ItemUtils.createItem(materialName)?.let {
//            item.setType(it)
//        }
        item.setType(type)

        section.getString("item_name")?.takeIf { it.isNotBlank() }?.let { name ->
            item.setItemName(name.toMiniMessage())
        }

        section.getStringList("lore").let { loreList ->
            item.setLore(loreList.map { it.toMiniMessage() })
        }

        section.getBoolean("unbreakable", false).let { item.setUnbreakable(it) }
        section.getBoolean("unstackable", false).let { item.setUnstackable(it) }

//        section.getInt("model_data", 0).takeIf { it > 0 }?.let { modelData ->
//            val isCustomItem = materialName.contains(":") && !materialName.contains("MINECRAFT:")
//            item.setCustomModelData(if (isCustomItem) -1 else modelData)
//        }

        parseDataComponents(item)
        parseVanillaSections(item)

        return item
    }

    fun parseDataComponents(item: ItemBuilder) {
        val components = section.getConfigurationSection("Components") ?: return
        handleLegacyComponents(item, components)
    }

    fun handleLegacyComponents(item: ItemBuilder, components: ConfigurationSection) {
        components.getBoolean("durability.damage_block_break").let {
            item.setDamagedOnBlockBreak(it)
        }
        components.getBoolean("durability.damage_entity_hit").let {
            item.setDamagedOnEntityHit(it)
        }
        maxOf(components.getInt("durability.value"), components.getInt("durability", 1)).let {
            item.setDurability(it)
        }

        components.getBoolean("hide_tooltip", false).let { item.setHideTooltip(it) }

        components.getConfigurationSection("tool")?.let { toolSection ->
            parseToolComponent(item, toolSection)
        }

        components.getConfigurationSection("food")?.let { foodSection ->
            parseFoodComponent(item, foodSection)
        }

        components.getConfigurationSection("equippable")?.let { equippedSection ->
            parseEquippableComponent(item, equippedSection)
        }

        if (!VersionUtil.isVersionAtOrAbove("1.21.2")) return

        components.getConfigurationSection("use_cooldown")?.let {
            runCatching {
                ItemStack(Material.PAPER).itemMeta?.useCooldown?.apply {
//                    cooldownGroup = NamespacedKey.fromString(
//                        it.getString("group") ?: "grassrpgitem:${RPGItems.getIdByItem(item)}"
//                    )
                    cooldownSeconds = maxOf(it.getDouble("seconds", 1.0), 0.0).toFloat()
                    item.setUseCooldownComponent(this)
                }
            }
//                .onFailure { e ->
//                GrassRPGItem.instance.logger.warning("Error setting UseCooldownComponent: This component is not available in your server version")
//                // if (Settings.DEBUG.toBool()) e.printStackTrace()
//            }
        }

        components.getString("tooltip_style")?.let(NamespacedKey::fromString)?.let(item::setTooltipStyle)

        components.getString("item_model")?.let(NamespacedKey::fromString)?.let(item::setItemModel)

        components.getInt("enchantable").takeIf { it > 0 }?.let { item.setEnchantable(it) }

    }

    private fun parseVanillaSections(item: ItemBuilder) {
        section.getStringList("ItemFlags").mapNotNull {
            runCatching { ItemFlag.valueOf(it.uppercase()) }.getOrNull()
        }.takeIf { it.isNotEmpty() }?.let { item.addItemFlags(*it.toTypedArray()) }

        section.getConfigurationSection("Enchantments")?.let { enchSection ->
            enchSection.getKeys(false).forEach { enchName ->
                val key = NamespacedKey.minecraft(enchName.lowercase())
                val enchant = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.ENCHANTMENT)
                    .get(key)

                if (enchant != null) {
                    val level = enchSection.getInt(enchName, 1)
                    item.addEnchant(enchant, level)
                }
            }
        }

        section.getString("rarity")?.let {
            runCatching { item.setRarity(ItemRarity.valueOf(it.uppercase())) }
        }

//        section.getStringList("damage_resistant").mapNotNull { tagName ->
//            DamageTypeTagRegistry.getTag(tagName)
//        }.takeIf { it.isNotEmpty() }?.let { tags ->
//            item.setDamageResistant(*tags.toTypedArray())
//        }

        // @Deprecated("단일값만 적용됨")
//        section.getStringList("damage_resistant")
//            .mapNotNull { tagName -> DamageTypeTagRegistry.getTag(tagName) }
//            .takeIf { it.isNotEmpty() }?.let {
//                item.setDamageResistant(*it.toTypedArray())
//                println("itA: ${it.toTypedArray().joinToString()}")
//                println("it: $it")
//            }

        // TODO 임시 단일
        section.getString("damage_resistant")?.let { tagName ->
            DamageTypeTagRegistry.getTag(tagName)?.let { tag ->
                item.setDamageResistant(tag)
            }
        }

        section.getBoolean("Glider", false).let { item.setGlider(it) }

        section.getInt("max_stack_size", 64).let { item.setMaxStackSize(it) }

    }

//    private val damageTypeTagMap: Map<String, Tag<DamageType>> by lazy {
//        val clazz = DamageTypeTags::class.java
//        clazz.fields
//            .filter { field ->
//                Tag::class.java.isAssignableFrom(field.type) &&
//                        java.lang.reflect.Modifier.isStatic(field.modifiers)
//            }
//            .mapNotNull { field ->
//                try {
//                    @Suppress("UNCHECKED_CAST")
//                    val value = field.get(null) as? Tag<DamageType>
//                    if (value != null) field.name to value else null
//                } catch (_: Exception) {
//                    null
//                }
//            }
//            .toMap()
//    }

//    private fun parseDamageResistantTags(item: ItemBuilder, section: ConfigurationSection) {
//        section.getStringList("damage_resistant").mapNotNull { tagName ->
//            damageTypeTagMap[tagName.uppercase()]
//        }.takeIf { it.isNotEmpty() }?.let { tags ->
//            item.setDamageResistant(*tags.toTypedArray())
//        }
//    }

    private fun parseToolComponent(item: ItemBuilder, toolSection: ConfigurationSection) {
        val toolComponent = ItemStack(type).itemMeta.tool
        toolComponent.damagePerBlock = toolSection.getInt("damage_per_block", 1).coerceAtLeast(0)
        toolComponent.defaultMiningSpeed =
            toolSection.getDouble("default_mining_speed", 1.0).toFloat().coerceAtLeast(0f)

        toolSection.getMapList("rules").forEach { rule ->
            val map = rule ?: return@forEach
            val speed = map["speed"].toString().toFloatOrNull() ?: 1f
            val correctForDrops = map["correct_for_drops"].toString().toBoolean()

            val materials = (map["materials"] as? List<*>)?.mapNotNull {
                runCatching { Material.valueOf(it.toString()) }.getOrNull()?.takeIf { it.isBlock }
            }?.toMutableSet() ?: mutableSetOf()

            map["material"]?.toString()?.let {
                runCatching { Material.valueOf(it) }.getOrNull()?.takeIf { it.isBlock }?.let(materials::add)
            }

            val tags = (map["tags"] as? List<*>)?.mapNotNull {
                NamespacedKey.fromString(it.toString())?.let { key ->
                    Bukkit.getTag(Tag.REGISTRY_BLOCKS, key, Material::class.java)
                }
            }?.toMutableSet() ?: mutableSetOf()

            map["tag"]?.toString()?.let {
                NamespacedKey.fromString(it)?.let { key ->
                    Bukkit.getTag(Tag.REGISTRY_BLOCKS, key, Material::class.java)?.let(tags::add)
                }
            }

            if (materials.isNotEmpty()) toolComponent.addRule(materials, speed, correctForDrops)
            tags.forEach { tag -> toolComponent.addRule(tag, speed, correctForDrops) }
        }

        item.setToolComponent(toolComponent)
    }

    private fun parseFoodComponent(item: ItemBuilder, foodSection: ConfigurationSection) {
        val foodComponent = ItemStack(type).itemMeta.food
        foodComponent.nutrition = foodSection.getInt("nutrition", 1).coerceAtLeast(0)
        foodComponent.saturation = foodSection.getDouble("saturation", 0.0).toFloat().coerceIn(0f, 100f)
        foodComponent.setCanAlwaysEat(foodSection.getBoolean("can_always_eat", false))
        item.setFoodComponent(foodComponent)
    }

    private fun parseEquippableComponent(item: ItemBuilder, equippableSection: ConfigurationSection) {
        val equippableComponent = ItemStack(type).itemMeta.equippable

        equippableSection.getString("slot")?.let {
            runCatching { EquipmentSlot.valueOf(it.uppercase()) }
                .getOrNull()
                ?.let { slot -> equippableComponent.setSlot(slot) }
        }

        item.setEquippable(equippableComponent)
    }



}