package org.ttlzmc.xmc.themes.beans

interface Styled {
    fun applyStyle(configuration: ThemeConfiguration)

    companion object {
        private val styledObjects = mutableListOf<Styled>()

        fun registerStyled(styled: Styled) {
            this.styledObjects.add(styled)
        }

        fun notifyThemeChanged(theme: Theme) {
            this.styledObjects.forEach {
                it.applyStyle(theme.container)
            }
        }
    }
}