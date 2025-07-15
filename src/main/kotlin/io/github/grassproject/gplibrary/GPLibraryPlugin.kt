package io.github.grassproject.gplibrary

import io.github.grassproject.gplibrary.util.Register
import io.github.grassproject.gplibrary.test.PlayerJoin
import org.bukkit.plugin.java.JavaPlugin

class GPLibraryPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: GPLibraryPlugin
            private set
    }

    override fun onEnable() {
        instance = this

        Register(this).resistEventListener(PlayerJoin())
    }
}