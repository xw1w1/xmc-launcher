package org.ttlzmc.xmc.launcher.page

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import org.ttlzmc.xmc.launcher.ThemeManager
import org.ttlzmc.xmc.launcher.UIPage
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.Theme
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import org.ttlzmc.xmc.translations.I18n

object WelcomePage : UIPage() {

    /**
     * Stored wrappers for themes
     */
    private val themeBoxes = mutableListOf<VBox>()

    override fun createContents() {
        this.children.addAll(
            Label("Welcome to XMC").apply {
                font = Font.font("Segoe UI", FontWeight.BOLD, 24.0)
                textFill = Color.WHITE
            }
        )
        Styled.registerStyled(this)
    }

    fun createThemesBox() {
        this.children.add(HBox().apply {
            this.spacing = 12.0
            val themes = ThemeManager.getHandle().getAllThemes()
            themes.forEach {
                this.children.add(this@WelcomePage.createThemeWrapper(it))
            }
        })
    }

    private fun createThemeWrapper(theme: Theme): VBox {
        return VBox().apply {
            val text = Text(I18n.translate(theme.themeIdentifier)).apply {
                fill = Color.WHITE
                font = Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16.0)
                alignment = Pos.TOP_CENTER
            }
            val applyTheme = Button("Apply").apply {
                setOnAction {
                    ThemeManager.setLauncherTheme(theme)
                    Styled.notifyThemeChanged(theme)
                }
                this.styleClass.add("theme-box-button")
            }

            this.minHeight = 90.0
            this.minWidth = 125.0

            this.children.addAll(text, applyTheme)
            this@WelcomePage.themeBoxes.add(this)
        }
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        this.themeBoxes.forEach {
            it.styleClass.add("theme-box")
        }
    }
}