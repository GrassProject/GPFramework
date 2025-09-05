package io.github.grassproject.framework.config

import io.github.grassproject.framework.core.GPPlugin

abstract class GPConfig<T : GPPlugin>(val plugin: T) {
    private val configs = mutableMapOf<String, ConfigFile>()

    protected abstract fun register()

    protected fun config(name: String, autoCreate: Boolean = false) =
        ConfigFile(plugin.dataFolder, name, autoCreate).also { configs[name] = it }

    operator fun get(name: String) = configs[name]

    fun saveAll() = configs.values.forEach(ConfigFile::save)
    fun reloadAll() = configs.values.forEach(ConfigFile::reload)

    fun registerAll() {
        register()
        reloadAll()
    }
}