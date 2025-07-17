package io.github.grassproject.gplibrary.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Config(private val plugin: JavaPlugin) {

    fun loadYaml(path: String): YamlConfiguration = loadYaml(getFile(path))

    fun loadYaml(file: File): YamlConfiguration {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            plugin.saveResource(file.name, false)
        }
        return YamlConfiguration.loadConfiguration(file)
    }

    fun setValue(path: String, configPath: String, value: Any) =
        setValue(getFile(path), configPath, value)

    fun setValue(file: File, configPath: String, value: Any) {
        val config = YamlConfiguration.loadConfiguration(file)
        config.set(configPath, value)
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun create(path: String): Boolean = create(getFile(path))

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

    fun createFromResource(path: String): Boolean =
        createFromResource(getFile(path), path)

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

    operator fun get(path: String): YamlConfiguration = loadYaml(path)

    fun getFile(path: String): File = File(plugin.dataFolder, path)

    fun fileExists(path: String): Boolean = getFile(path).exists()

    fun delete(path: String): Boolean = getFile(path).delete()

    fun saveYaml(config: YamlConfiguration, path: String) =
        saveYaml(config, getFile(path))

    fun saveYaml(config: YamlConfiguration, file: File) {
        try {
            file.parentFile?.mkdirs()
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun copyFile(from: File, to: File): Boolean {
        if (!from.exists()) return false
        to.parentFile?.mkdirs()
        return try {
            from.inputStream().use { input ->
                to.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun listFiles(dirPath: String, filter: (File) -> Boolean = { true }): List<File> {
        val dir = getFile(dirPath)
        return if (dir.exists() && dir.isDirectory) {
            dir.listFiles()?.filter(filter) ?: emptyList()
        } else emptyList()
    }
}
