package io.github.grassproject.framework.config

import io.github.grassproject.framework.core.GPPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

fun GPPlugin.saveResource(resource: String, file: File) {
    if (file.exists()) return
    file.parentFile.mkdirs()

    val inputStream = getResource(resource)
    // ?: throw IllegalArgumentException("Resource '$resource' not found in plugin JAR!")

    if (inputStream != null) {
        file.outputStream().use { output ->
            inputStream.use { input ->
                input.copyTo(output)
            }
        }
    } else {
        file.createNewFile()
    }
}

fun GPPlugin.init(vararg configs: GPFile) {
    configs.forEach { yaml ->
        val file = File(dataFolder, yaml.name)
        if (!file.exists()) saveResource(yaml.name, file)

        yaml.reload(file)
    }
}

abstract class Config {

    abstract var config: FileConfiguration
    open var autoSave: Boolean = true

    open operator fun contains(key: String) = config.contains(key)
    open operator fun get(key: String) = config[key]
    open operator fun set(key: String, value: Any?) {
        config.set(key, value)
        if (autoSave) save()
    }

    open val sections: Set<String> get() = config.getKeys(false)
    open fun section(path: String) = config.getConfigurationSection(path)
    open fun keys(path: String, deep: Boolean = false) = section(path)?.getKeys(deep) ?: emptySet()

    open fun list(path: String) = config.getList(path).orEmpty()
    open fun string(path: String, def: String = "") = config.getString(path, def)!!
    open fun stringList(path: String) = config.getStringList(path)
    open fun boolean(path: String, def: Boolean = false) = config.getBoolean(path, def)
    open fun booleanList(path: String) = config.getBooleanList(path)
    open fun int(path: String, def: Int = 0) = config.getInt(path, def)
    open fun intList(path: String) = config.getIntegerList(path)
    open fun long(path: String, def: Long = 0L) = config.getLong(path, def)
    open fun longList(path: String) = config.getLongList(path)
    open fun double(path: String, def: Double = 0.0) = config.getDouble(path, def)
    open fun doubleList(path: String) = config.getDoubleList(path)

    open fun <T: Any> getValue(path: String, clazz: KClass<T>): T {
        return clazz.cast(config.get(path))
    }

    open fun <T: Any> getList(path: String, clazz: KClass<T>): List<T> {
        val t=config.getList(path) ?: emptyList()
        return t.filterIsInstance(clazz.java)
    }

    inline fun <reified T : Enum<T>> enum(path: String, def: T): T {
        val str = string(path, def.name)
        return runCatching { enumValueOf<T>(str.uppercase()) }.getOrDefault(def)
    }

    fun <T : Enum<T>> setEnum(path: String, value: T) = set(path, value.name)

    open fun save() {}
}

open class ConfigFile(open val file: File) : Config() {
    override var config: FileConfiguration = YamlConfiguration.loadConfiguration(file)
        set(value) {
            field = value
            save()
        }

    override fun save() {
        config.save(file)
    }

    fun reload(file: File? = null) {
        config = if (file != null) YamlConfiguration.loadConfiguration(file)
        else YamlConfiguration.loadConfiguration(this.file)
    }
}

open class GPFile(pluginFolder: File, open var name: String) :
    ConfigFile(File(pluginFolder, name)) {

    constructor(pluginFolder: File, name: String, autoCreate: Boolean = false) : this(pluginFolder, name) {
        if (autoCreate && !file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
    }

    constructor(pluginFolderPath: String, name: String, autoCreate: Boolean = false) : this(File(pluginFolderPath), name, autoCreate)
}