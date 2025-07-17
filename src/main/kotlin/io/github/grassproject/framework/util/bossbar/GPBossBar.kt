package io.github.grassproject.framework.util.bossbar

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.EnumSet
import java.util.UUID

class GPBossBar(
    message: Component,
    color: BossBar.Color,
    overlay: BossBar.Overlay,
    flags: Set<BossBar.Flag>,
    progress: Float
) {

    val uuid: UUID = UUID.randomUUID()
    private val bossBar: BossBar = BossBar.bossBar(message, progress, color, overlay, EnumSet.copyOf(flags))

    var message: Component = message
        set(value) {
            field = value
            bossBar.name(value)
        }

    var color: BossBar.Color = color
        set(value) {
            field = value
            bossBar.color(value)
        }

    var overlay: BossBar.Overlay = overlay
        set(value) {
            field = value
            bossBar.overlay(value)
        }

    private var flags: EnumSet<BossBar.Flag> = EnumSet.copyOf(flags)
        set(value) {
            field = EnumSet.copyOf(value)
            bossBar.flags(field)
        }

    fun getFlags(): Set<BossBar.Flag> = EnumSet.copyOf(flags)

    fun addFlag(flag: BossBar.Flag) {
        if (flags.add(flag)) {
            bossBar.flags(flags)
        }
    }

    fun removeFlag(flag: BossBar.Flag) {
        if (flags.remove(flag)) {
            bossBar.flags(flags)
        }
    }

    fun setFlags(flags: Set<BossBar.Flag>) {
        this.flags = EnumSet.copyOf(flags)
    }

    var progress: Float = progress
        set(value) {
            field = value.coerceIn(0f, 1f)
            bossBar.progress(field)
        }

    fun addViewer(player: Player) {
        bossBar.addViewer(player)
    }

    fun removeViewer(player: Player) {
        bossBar.removeViewer(player)
    }
}