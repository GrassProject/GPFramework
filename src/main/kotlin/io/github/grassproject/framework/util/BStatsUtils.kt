package io.github.grassproject.framework.util

import io.github.grassproject.framework.GPFrameworkPlugin
import io.github.grassproject.framework.Metrics
import org.bukkit.plugin.java.JavaPlugin

class BStatsUtils(id: Int) {

    init {
        Metrics(GPFrameworkPlugin.instance, id)
    }

//    companion object {
//        fun registerMetrics(id: Int): Metrics {
//            return Metrics(GPFrameworkPlugin.instance, id)
//        }
//    }

}