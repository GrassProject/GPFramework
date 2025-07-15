package io.github.grassproject.gplibrary.item.util

import org.bukkit.Tag
import org.bukkit.damage.DamageType
import org.bukkit.tag.DamageTypeTags

object DamageTypeTagRegistry {
    val damageTypeTagMap: Map<String, Tag<DamageType>> = mapOf(
        "ALWAYS_HURTS_ENDER_DRAGONS" to DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS,
        "ALWAYS_KILLS_ARMOR_STANDS" to DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS,
        "ALWAYS_MOST_SIGNIFICANT_FALL" to DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL,
        "ALWAYS_TRIGGERS_SILVERFISH" to DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH,
        "AVOIDS_GUARDIAN_THORNS" to DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
        "BURN_FROM_STEPPING" to DamageTypeTags.BURN_FROM_STEPPING,
        "BURNS_ARMOR_STANDS" to DamageTypeTags.BURNS_ARMOR_STANDS,
        "BYPASSES_ARMOR" to DamageTypeTags.BYPASSES_ARMOR,
        "BYPASSES_EFFECTS" to DamageTypeTags.BYPASSES_EFFECTS,
        "BYPASSES_ENCHANTMENTS" to DamageTypeTags.BYPASSES_ENCHANTMENTS,
        "BYPASSES_INVULNERABILITY" to DamageTypeTags.BYPASSES_INVULNERABILITY,
        "BYPASSES_RESISTANCE" to DamageTypeTags.BYPASSES_RESISTANCE,
        "BYPASSES_SHIELD" to DamageTypeTags.BYPASSES_SHIELD,
        "BYPASSES_WOLF_ARMOR" to DamageTypeTags.BYPASSES_WOLF_ARMOR,
        "CAN_BREAK_ARMOR_STAND" to DamageTypeTags.CAN_BREAK_ARMOR_STAND,
        "DAMAGES_HELMET" to DamageTypeTags.DAMAGES_HELMET,
        "IGNITES_ARMOR_STANDS" to DamageTypeTags.IGNITES_ARMOR_STANDS,
        "IS_DROWNING" to DamageTypeTags.IS_DROWNING,
        "IS_EXPLOSION" to DamageTypeTags.IS_EXPLOSION,
        "IS_FALL" to DamageTypeTags.IS_FALL,
        "IS_FIRE" to DamageTypeTags.IS_FIRE,
        "IS_FREEZING" to DamageTypeTags.IS_FREEZING,
        "IS_LIGHTNING" to DamageTypeTags.IS_LIGHTNING,
        "IS_PLAYER_ATTACK" to DamageTypeTags.IS_PLAYER_ATTACK,
        "IS_PROJECTILE" to DamageTypeTags.IS_PROJECTILE,
        // "MACE_SMASH" to DamageTypeTags.MACE_SMASH, TODO 1.21.4에 없음
        "NO_ANGER" to DamageTypeTags.NO_ANGER,
        "NO_IMPACT" to DamageTypeTags.NO_IMPACT,
        "NO_KNOCKBACK" to DamageTypeTags.NO_KNOCKBACK,
        "PANIC_CAUSES" to DamageTypeTags.PANIC_CAUSES,
        "PANIC_ENVIRONMENTAL_CAUSES" to DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES,
        "WITCH_RESISTANT_TO" to DamageTypeTags.WITCH_RESISTANT_TO,
        "WITHER_IMMUNE_TO" to DamageTypeTags.WITHER_IMMUNE_TO,
    )

    fun getTag(name: String): Tag<DamageType>? {
        return damageTypeTagMap[name.uppercase()]
    }
}
