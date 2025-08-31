package io.github.grassproject.framework.config

import io.github.grassproject.framework.core.GPPlugin

abstract class GPConfigs<T : GPPlugin>(val plugin: T) {
    private val configs = mutableMapOf<String, GPConfig>()

    protected abstract fun register()

    protected fun config(name: String, autoCreate: Boolean = false) =
        GPConfig(plugin.dataFolder, name, autoCreate).also { configs[name] = it }

    operator fun get(name: String) = configs[name]

    fun saveAll() = configs.values.forEach(GPConfig::save)
    fun reloadAll() = configs.values.forEach(GPConfig::reload)

    fun registerAll() {
        register()
        reloadAll()
    }
}
