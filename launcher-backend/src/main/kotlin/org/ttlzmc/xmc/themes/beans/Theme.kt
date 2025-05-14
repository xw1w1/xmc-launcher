package org.ttlzmc.xmc.themes.beans

class Theme(
    val themeIdentifier: String,
    val cssFileName: String,
    val container: ThemeConfiguration
) {
    fun getCssFile() = this.cssFileName
}