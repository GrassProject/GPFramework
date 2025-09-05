package io.github.grassproject.framework.util

import java.io.File
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object McLogsUtil {

    private val logFile = File("logs/latest.log")
    private val client = HttpClient.newHttpClient()

    fun readLogs(): List<String> = logFile.takeIf { it.exists() }?.readLines() ?: emptyList()

    fun getLastLogs(limit: Int = 10): List<String> = readLogs().takeLast(limit)

    fun findLogs(keyword: String): List<String> =
        readLogs().filter { it.contains(keyword, ignoreCase = true) }

    fun uploadLogs(): String? {
        val logText = readTextSafe().takeIf { it.isNotBlank() } ?: return null

        return runCatching {
            val body = "content=${URLEncoder.encode(logText, "UTF-8")}"
            val request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mclo.gs/1/log"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
            "\"id\":\"".let { id ->
                if (response.contains(id)) "https://mclo.gs/${response.substringAfter(id).substringBefore("\"")}"
                else null
            }
        }.getOrNull()
    }

    private fun readTextSafe(): String = runCatching { logFile.readText() }.getOrDefault("")
}
