package io.github.grassproject.gplibrary.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Config(private val plugin: JavaPlugin) {

    fun loadYaml(path: String): YamlConfiguration = loadYaml(File(plugin.dataFolder, path))

    fun loadYaml(file: File): YamlConfiguration {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            plugin.saveResource(file.name, false)
        }
        return YamlConfiguration.loadConfiguration(file)
    }

    fun setValue(path: String, configPath: String, value: Any) =
        setValue(File(plugin.dataFolder, path), configPath, value)

    fun setValue(file: File, configPath: String, value: Any) {
        val config = YamlConfiguration.loadConfiguration(file)
        config.set(configPath, value)
        config.save(file)
    }

    fun create(path: String): Boolean = create(File(plugin.dataFolder, path))

    fun create(file: File): Boolean {
        if (file.exists()) return false
        file.parentFile?.mkdirs()
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun createFromResource(path: String): Boolean = createFromResource(File(plugin.dataFolder, path), path)

    fun createFromResource(file: File, resourcePath: String): Boolean {
        if (file.exists()) return false
        file.parentFile?.mkdirs()
        val input = plugin.getResource(resourcePath) ?: return false
        return try {
            input.use { it.copyTo(file.outputStream()) }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}
