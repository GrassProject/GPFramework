package io.github.grassproject.framework.github

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.framework.util.GPLogger
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@Deprecated("TEST")
object GithubAPI {
    private const val ORG="GrassProject"
    private const val REPO="GPFramework"
    private const val GITHUB_API="https://api.github.com/repos/${ORG}/${REPO}/releases/latest"

    fun isLatest(instance: GPPlugin): Boolean {
        val latest = getLatest() ?: return true
        val latestRaw = latest.get("ver").asString
        val currentRaw = instance.version

        fun parseVersion(ver: String): Pair<List<Int>, String?> {
            val parts = ver.split("-", limit = 2)
            val numbers = parts[0].split(".").mapNotNull { it.toIntOrNull() }
            val tag = if (parts.size > 1) parts[1].lowercase() else null
            return numbers to tag
        }

        val (latestNums, latestTag) = parseVersion(latestRaw)
        val (currentNums, currentTag) = parseVersion(currentRaw)

        val maxLen = maxOf(latestNums.size, currentNums.size)
        for (i in 0 until maxLen) {
            val cur = currentNums.getOrNull(i) ?: 0
            val lat = latestNums.getOrNull(i) ?: 0
            if (cur < lat) return false
            if (cur > lat) return true
        }

        return when {
            currentTag == null && latestTag != null -> true
            currentTag != null && latestTag == null -> false
            currentTag == null && latestTag == null -> true
            else -> {
                val order = listOf("ALPHA", "BETA", "RC", "SNAPSHOT")
                val curIndex = order.indexOfFirst { currentTag!!.contains(it) }
                    .takeIf { it >= 0 } ?: Int.MAX_VALUE
                val latIndex = order.indexOfFirst { latestTag!!.contains(it) }
                    .takeIf { it >= 0 } ?: Int.MAX_VALUE
                curIndex >= latIndex
            }
        }
    }

    fun downloadAndReplace(plugin: GPPlugin): Boolean {
        val latest = getLatest() ?: return false
        val downloadUrl = latest.get("download")?.asString ?: return false


        return try {
            val pluginsDir = File("plugins")
            val newFile = File(pluginsDir, "${plugin.name}.jar.new")
            URL(downloadUrl).openStream().use { input ->
                Files.copy(input, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }
            GPLogger.info("새 버전을 다운로드했습니다. 서버 재시작 후 적용됩니다.")
            true
        } catch (e: Exception) {
            GPLogger.warning("Fail to download UPDATE: ${e.message}")
            false
        }
    }

    fun getLatest(): JsonObject {
        val json= JsonObject()
        val data=Gson().fromJson(fetchData(GITHUB_API), JsonObject::class.java)

        json.addProperty("ver", data.get("tag_name").asString)
        json.addProperty("published", data.get("published_at").asString)

        val assets = data.getAsJsonArray("assets")
        val downloadUrl = assets[0].asJsonObject.get("browser_download_url").asString
        json.addProperty("download", downloadUrl)

        return json
    }

    private fun fetchData(url: String): String {
        return URL(url).httpRequest {
            requestMethod = "GET"
            setRequestProperty("Accept", "application/vnd.github.v3+json")
//            setRequestProperty("Authorization", "")
            inputStream.bufferedReader().use { it.readText() }
        }
    }

    private fun <T> URL.httpRequest(requester: (HttpURLConnection.() -> T)): T {
        return with(openConnection() as HttpURLConnection) { requester.invoke(this) }
    }
}