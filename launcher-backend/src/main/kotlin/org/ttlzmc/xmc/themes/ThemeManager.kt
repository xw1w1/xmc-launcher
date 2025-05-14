package org.ttlzmc.xmc.themes

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import javafx.scene.paint.Color
import org.ttlzmc.xmc.beans.ColorAdapter
import org.ttlzmc.xmc.parseJson
import org.ttlzmc.xmc.platform.XMCConstants.LAUNCHER_HOME_DIRECTORY
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.Theme
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import java.io.File

class ThemeManager {
    private var theme: Theme? = null
    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorAdapter())
        .setPrettyPrinting()
        .create()

    private val themes = mutableMapOf<String, Theme>()
    private val loadedStylesheets = mutableSetOf<String>()

    fun getAppliedTheme(): Theme {
        val launcherConfiguration = File(LAUNCHER_HOME_DIRECTORY, "config/launcher.json").parseJson()
        val themeIdentifier = launcherConfiguration.get("launcher.theme").asString
        val theme = getTheme(themeIdentifier)
        return theme.also { this.theme = theme }
    }

    fun loadThemes() {
        try {
            val themesFile = File(LAUNCHER_HOME_DIRECTORY, "assets/themes/themes.json")
            val jsonString = themesFile.readText()

            val type = object : TypeToken<Map<String, ThemeConfiguration>>() {}.type
            val themesMap: Map<String, ThemeConfiguration> = gson.fromJson(jsonString, type)

            themesMap.values.forEach { config ->
                val theme = Theme(
                    themeIdentifier = config.identifier,
                    cssFileName = config.cssFilePath,
                    container = config
                )

                themes[config.identifier] = theme
                loadThemeStylesheet(theme)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadThemeStylesheet(theme: Theme) {
        try {
            val cssPath = "assets/themes/${theme.getCssFile()}"
            val cssFile = File(LAUNCHER_HOME_DIRECTORY, cssPath)

            if (cssFile.exists()) {
                val stylesheet = cssFile.toURI().toString()
                if (!loadedStylesheets.contains(stylesheet)) {
                    loadedStylesheets.add(stylesheet)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun applyTheme(themeId: String) {
        val theme = themes[themeId] ?: throw IllegalArgumentException("Theme $themeId not found")
        Styled.notifyThemeChanged(theme)
    }

    fun getTheme(themeId: String): Theme = themes[themeId] ?: throw IllegalArgumentException("Theme $themeId not found")
    fun getCurrentTheme() = theme
    fun getAllThemes(): List<Theme> = themes.values.toList()
}