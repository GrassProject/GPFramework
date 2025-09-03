package io.github.grassproject.framework.skript

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import io.github.grassproject.framework.GPFrameworkPlugin
import io.github.grassproject.framework.util.GPLogger

class SkriptLangModule {

    companion object {
        lateinit var addon: SkriptAddon
            private set
    }

    fun register() {
        addon = Skript.registerAddon(GPFrameworkPlugin.instance)
        GPLogger.info("Skript has been enabled!")
    }
}