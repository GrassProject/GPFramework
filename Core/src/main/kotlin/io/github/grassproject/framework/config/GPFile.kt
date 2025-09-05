package io.github.grassproject.framework.config

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class GPFile(
    folder: File,
    name: String,
    extension: String
) {
    private val file = File(folder, "$name.$extension")
    val exists: Boolean get() = file.exists()
    val extension: EXTENSION = EXTENSION.from(extension)

    fun reload(): File = File(file.path)
    fun load() {
        if (file.exists()) return
        file.createNewFile()
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
