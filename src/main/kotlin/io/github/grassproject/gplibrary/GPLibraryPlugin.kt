package io.github.grassproject.gplibrary

import org.bukkit.plugin.java.JavaPlugin

class GPLibraryPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: GPLibraryPlugin
            private set
    }

    override fun onEnable() {
        instance = this
    }
}