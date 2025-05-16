package org.ttlzmc.xmc.launcher.page

import org.ttlzmc.xmc.launcher.UIPage
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

object WelcomePage : UIPage() {

    override fun createContents() {
        this.children.addAll(
            Label("Welcome to XMC").apply {
                font = Font.font("Segoe UI", FontWeight.BOLD, 24.0)
                textFill = Color.WHITE
            }
        )
    }

    fun createThemesBox() {

    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        TODO("Not yet implemented")
    }
}