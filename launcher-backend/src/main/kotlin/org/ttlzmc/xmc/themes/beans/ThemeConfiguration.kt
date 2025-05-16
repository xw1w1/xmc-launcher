package org.ttlzmc.xmc.themes.beans

import com.google.gson.annotations.SerializedName
import javafx.scene.paint.Color

data class ThemeConfiguration(
    @SerializedName("darkTheme") val isDarkTheme: Boolean,
    @SerializedName("useMica") val useMica: Boolean,
    @SerializedName("identifier") val identifier: String,
    @SerializedName("cssFilePath") val cssFilePath: String,
    // ThemeContainer:
    @SerializedName("backgroundFillColor") val backgroundFillColor: Color,
    @SerializedName("backgroundSubColor") val backgroundSubColor: Color,
    @SerializedName("shadowColor") val shadowColor: Color
)