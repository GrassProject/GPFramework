package io.github.grassproject.framework.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.grassproject.framework.GPFrameworkPlugin
import java.io.File

object translate {
    private val plugin= GPFrameworkPlugin.instance

    private fun jsonGenerator(): File {
        plugin.reloadConfig()
        val lang=plugin.config.getString("language") ?: "ko"
        val json= File("${plugin.dataFolder}/lang", "${lang}.json")
        return json
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
        val json= jsonGenerator()
        return Gson().fromJson(json.readText(), JsonObject::class.java)
            .get(string)?.asString ?: string
    }

    fun fromList(string: String): MutableList<String> {
        val json= jsonGenerator()
        val array=Gson().fromJson(json.readText(), JsonObject::class.java)
            .get(string)
            .asJsonArray.mapNotNull { it.asString }.toMutableList()
        return array
    }
}