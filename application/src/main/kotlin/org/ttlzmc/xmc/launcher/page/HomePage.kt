package org.ttlzmc.xmc.launcher.page

import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import org.ttlzmc.xmc.launcher.UIPage
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration

object HomePage : UIPage() {

    override fun createContents() {
        this.children.addAll(
            Label("Home").apply {
                font = Font.font("Segoe UI", FontWeight.BOLD, 24.0)
                textFill = Color.WHITE
            }
        )
        Styled.registerStyled(this)
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        TODO("Not yet implemented")
    }
}