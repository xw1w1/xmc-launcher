package org.ttlzmc.xmc.launcher

import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyObjectWrapper
import org.ttlzmc.xmc.themes.beans.Theme
import org.ttlzmc.xmc.themes.ThemeManager

object ThemeManager {
    private val internalThemeManager = ThemeManager()
    private val themeProperty: ObjectProperty<Theme> = ReadOnlyObjectWrapper()

    fun initialize() {
        internalThemeManager.loadThemes()
        themeProperty.set(internalThemeManager.getAppliedTheme())
    }

    fun setLauncherTheme(theme: Theme) {
        themeProperty.set(theme)
        internalThemeManager.applyTheme(theme.themeIdentifier)
    }

    fun getLauncherTheme(): Theme {
        return internalThemeManager.getCurrentTheme()!!
    }
}