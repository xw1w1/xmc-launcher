package org.ttlzmc.xmc

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import javafx.scene.paint.Color
import org.ttlzmc.xmc.beans.ColorAdapter
import org.ttlzmc.xmc.beans.IndexFile
import org.ttlzmc.xmc.platform.XMCConstants.LAUNCHER_ASSETS_DIRECTORY
import org.ttlzmc.xmc.platform.XMCConstants.LAUNCHER_CONFIGS_DIRECTORY
import org.ttlzmc.xmc.platform.XMCConstants.LAUNCHER_HOME_DIRECTORY
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import org.ttlzmc.xmc.translations.Language
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI

object AssetDownloader {
    val FIRST_RUN_FLAG = File(LAUNCHER_HOME_DIRECTORY, ".xmc")

    val THEMES_FOLDER = File(LAUNCHER_ASSETS_DIRECTORY, "themes")
    val IMAGES_FOLDER = File(LAUNCHER_ASSETS_DIRECTORY, "images")
    val TRANSLATIONS_FOLDER = File(LAUNCHER_ASSETS_DIRECTORY, "translations")
    val PREFERENCES = File(LAUNCHER_CONFIGS_DIRECTORY, "launcher.json")

    const val REPO_BASE_URL = "https://raw.githubusercontent.com/xw1w1/xmc-assets/refs/heads/main/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorAdapter())
        .setPrettyPrinting()
        .create()

    fun isFirstRun(): Boolean = !FIRST_RUN_FLAG.exists()

    fun checkUpdates() {
        createEssentialFolders()
        if (isFirstRun()) {
            FIRST_RUN_FLAG.createNewFile()
        }
        downloadAssets()
    }

    fun downloadAssets() {
        try {
            createEssentialFolders()

            val indexContent = downloadString("${REPO_BASE_URL}assets/index.json")
            val index = gson.fromJson(indexContent, IndexFile::class.java)

            val allRemoteFiles = mutableListOf<String>().apply {
                add(index.themes) // "assets/themes/themes.json"
                add(index.translations)
            }

            val categoryIndexes = listOf(
                index.themes to "themes",
                index.translations to "translations",
                index.images to "images"
            )

            categoryIndexes.forEach { (categoryPath, categoryType) ->
                try {
                    val categoryContent = downloadString("${REPO_BASE_URL}$categoryPath")
                    val files = processCategory(categoryType, categoryContent)
                    allRemoteFiles.addAll(files)
                } catch (e: Exception) {
                    println("Error processing $categoryType: ${e.message}")
                }
            }

            allRemoteFiles.forEach { remotePath ->
                val localFile = File(LAUNCHER_HOME_DIRECTORY, remotePath)
                if (!localFile.exists()) {
                    println("Downloading missing file: $remotePath")
                    downloadFile("${REPO_BASE_URL}$remotePath", localFile)
                }
            }

            allRemoteFiles.forEach { remotePath ->
                val localFile = File(LAUNCHER_HOME_DIRECTORY, remotePath)
                if (!localFile.exists()) {
                    println("Downloading missing file: $remotePath")
                    downloadFile("${REPO_BASE_URL}$remotePath", localFile)
                }
            }

            if (!PREFERENCES.exists()) {
                PREFERENCES.parentFile?.mkdirs()
                PREFERENCES.writeText(gson.toJson(index.defaults))
            }
        } catch (e: JsonSyntaxException) {
            println("JSON Syntax Error: ${e.message}")
            e.printStackTrace()
        } catch (e: IOException) {
            println("Network/File Error: ${e.message}")
        }
    }

    private fun createEssentialFolders() {
        arrayOf(
            LAUNCHER_HOME_DIRECTORY,
            LAUNCHER_CONFIGS_DIRECTORY,
            THEMES_FOLDER,
            IMAGES_FOLDER,
            TRANSLATIONS_FOLDER
        ).forEach { it.mkdirs() }
    }

    private fun processCategory(categoryType: String, jsonContent: String): List<String> {
        return when (categoryType) {
            "themes" -> {
                val type = object : TypeToken<Map<String, ThemeConfiguration>>() {}.type
                gson.fromJson<Map<String, ThemeConfiguration>>(jsonContent, type)
                    .values.map { it.cssFilePath }
            }
            "translations" -> {
                val type = object : TypeToken<Map<String, Language>>() {}.type
                gson.fromJson<Map<String, Language>>(jsonContent, type)
                    .values.map { it.translationsFilePath }
            }
            "images" -> {
                val json = JsonParser.parseString(jsonContent).asJsonObject
                json.entrySet().map { it.value.asString }
            }
            else -> emptyList()
        }.also { list ->
            if (list.isEmpty()) {
                println("No files found for category: $categoryType")
            }
        }
    }

    private fun downloadFile(url: String, destFile: File) {
        try {
            destFile.parentFile?.mkdirs()

            (URI(url).toURL().openConnection() as? HttpURLConnection)?.run {
                requestMethod = "GET"
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream.use { input ->
                        destFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    println("Resource downloaded [$url]")
                } else {
                    println("Online resource at $url not found: [HTTP $responseCode]")
                }
                disconnect()
            }
        } catch (e: Exception) {
            println("Error downloading $url: ${e.message}")
        }
    }

    private fun downloadString(url: String): String {
        (URI(url).toURL().openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            try {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return inputStream.bufferedReader().readText()
                }
                throw RuntimeException("Online resource at $url not found: [HTTP $responseCode]")
            } finally {
                disconnect()
            }
        }
        throw IOException("Failed to connect")
    }
}