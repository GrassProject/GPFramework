package io.github.grassproject.framework.config

import io.github.grassproject.framework.core.GPPlugin

abstract class GPConfig<T : GPPlugin>(val plugin: T) {
    private val configs = mutableMapOf<String, GPFile>()

    protected abstract fun register()

    protected fun config(name: String, autoCreate: Boolean = false) =
        GPFile(plugin.dataFolder, name, autoCreate).also { configs[name] = it }

    operator fun get(name: String) = configs[name]

    fun saveAll() = configs.values.forEach(GPFile::save)
    fun reloadAll() = configs.values.forEach(GPFile::reload)

    fun registerAll() {
        register()
        reloadAll()
    }
}