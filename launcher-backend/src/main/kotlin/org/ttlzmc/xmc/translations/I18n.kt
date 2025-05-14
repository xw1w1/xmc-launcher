package org.ttlzmc.xmc.translations

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.ttlzmc.xmc.parseJson
import org.ttlzmc.xmc.platform.XMCConstants
import java.io.File

object I18n {
    private var usedLanguage: Language? = null
    private val translationsCache = mutableMapOf<Language, JsonObject>()

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun loadTranslations() {
        val translationsDir = File(XMCConstants.LAUNCHER_ASSETS_DIRECTORY, "translations")
        val translationsJson = File(translationsDir, "translations.json").parseJson()

        val type = object : TypeToken<Map<String, Language>>() {}.type
        val themesMap: Map<String, Language> = gson.fromJson(translationsJson, type)

        themesMap.values.forEach { config ->
            val language = Language(
                config.identifier,
                config.displayName,
                config.translationsFilePath
            )

            val translations = File(XMCConstants.LAUNCHER_HOME_DIRECTORY, config.translationsFilePath).parseJson()

            translationsCache[language] = translations
        }

        val configuration = File(XMCConstants.LAUNCHER_CONFIGS_DIRECTORY, "launcher.json").parseJson()
        val identifier = configuration.get("launcher.lang").asString
        usedLanguage = translationsCache.keys.firstOrNull { it.identifier == identifier }
    }

    fun setLocale(language: Language) {
        this.usedLanguage = language
    }

    fun translate(key: String, vararg args: Any): String {
        val cache = translationsCache[usedLanguage] ?: return key
        val translation = cache.get(key) ?: return key
        return translation.asString.format(args)
    }
}