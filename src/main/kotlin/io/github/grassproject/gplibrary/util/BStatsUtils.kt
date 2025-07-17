package io.github.grassproject.gplibrary.util

import io.github.grassproject.gplibrary.GPLibraryPlugin
import io.github.grassproject.gplibrary.Metrics
import org.bukkit.plugin.java.JavaPlugin

class BStatsUtils(plugin: JavaPlugin, id: Int) {

    init {
        Metrics(plugin, id)
    }

    companion object {
        fun registerMetrics(id: Int): Metrics {
            return Metrics(GPLibraryPlugin.instance, id)
        }
    }

}