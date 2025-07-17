package io.github.grassproject.framework.api.test

import io.github.grassproject.framework.config.Config
import org.bukkit.configuration.file.YamlConfiguration

interface IGPPlugin {
    val configManager: Config
    val configYaml: YamlConfiguration

    fun enable()
    fun disable()
    fun reload()
}