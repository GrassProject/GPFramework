package io.github.grassproject.framework.message

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.grassproject.framework.core.GPPlugin
import java.io.File

abstract class GTranslate<T : GPPlugin>(val plugin: T) {

    lateinit var jsonFile: File
        private set

    fun init() {
        plugin.reloadConfig()
        val lang = plugin.config.getString("language") ?: "korean"

        val langFolder = File(plugin.dataFolder, "language").apply { if (!exists()) mkdirs() }
        jsonFile = File(langFolder, "$lang.json")

        if (!jsonFile.exists()) {
            plugin.saveResource("language/$lang.json", false)
        }
    }

    fun literate(key: String, placeholders: Map<String, String>): String {
        var message = literate(key)
        placeholders.forEach { (placeholder, value) ->
            message = message.replace("{$placeholder}", value)
        }
        return message
    }

    fun fromList(key: String, placeholders: Map<String, String>): MutableList<String> {
        val messages = fromList(key)
        return messages.map { message ->
            var result = message
            placeholders.forEach { (placeholder, value) ->
                result = result.replace("{$placeholder}", value)
            }
            result
        }.toMutableList()
    }

    fun literate(string: String): String {
        return Gson().fromJson(jsonFile.readText(), JsonObject::class.java)[string]?.asString ?: string
    }

    fun fromList(string: String): MutableList<String> {
        val array = Gson().fromJson(jsonFile.readText(), JsonObject::class.java)[string]
            .asJsonArray.mapNotNull { it.asString }.toMutableList()
        return array
    }
}