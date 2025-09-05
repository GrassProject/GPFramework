package io.github.grassproject.framework.config

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class GPFile {
    private var file: File
    var extension: EXTENSION = EXTENSION.UNKNOWN
        private set

    constructor(folder: File, name: String, ext: String) {
        this.file = File(folder, "$name.$ext")
        this.extension = EXTENSION.from(ext)
    }

    constructor(path: String) {
        this.file = File(path)
        val ext = file.extension
        this.extension = EXTENSION.from(ext)
    }

    val exists: Boolean get() = file.exists()

    fun reload(): File = File(file.path)

    fun load() {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
    }

    fun toYml(): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(file)
    }

    fun toJson(): JsonObject {
        return file.reader().use { reader ->
            Gson().fromJson(reader, JsonObject::class.java)
        }
    }

    fun getContent(): String {
        return file.readText()
    }

    fun <D : Any> toData(dataClass: Class<D>): D? {
        return file.reader().use { reader ->
            Gson().fromJson(reader, dataClass)
        }
    }

    enum class EXTENSION(val ext: String) {
        JSON("json"),
        YAML("yml"),
        TXT("txt"),
        EMPTY(""),
        UNKNOWN("unknown");

        companion object {
            fun from(value: String): EXTENSION {
                return entries
                    .firstOrNull { it.ext.equals(value, ignoreCase = true) }
                    ?: UNKNOWN
            }
        }
    }
}
