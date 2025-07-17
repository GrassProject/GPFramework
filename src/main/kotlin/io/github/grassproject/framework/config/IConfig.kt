package io.github.grassproject.framework.config

import org.bukkit.configuration.file.YamlConfiguration

interface IConfig {
    val configManager: Config
    val configYaml: YamlConfiguration
}