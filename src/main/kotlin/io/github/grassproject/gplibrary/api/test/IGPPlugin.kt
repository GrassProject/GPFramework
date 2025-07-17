package io.github.grassproject.gplibrary.api.test

import io.github.grassproject.gplibrary.config.Config
import org.bukkit.configuration.file.YamlConfiguration

interface IGPPlugin {
    val configManager: Config
    val configYaml: YamlConfiguration

    fun enable()
    fun disable()
    fun reload()
}