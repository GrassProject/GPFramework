package io.github.grassproject.gplibrary.config

import org.bukkit.configuration.file.YamlConfiguration

interface IConfig {
    val configManager: Config
    val configYaml: YamlConfiguration
}