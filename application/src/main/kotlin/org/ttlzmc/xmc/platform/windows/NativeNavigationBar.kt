package org.ttlzmc.xmc.platform.windows

import javafx.beans.InvalidationListener
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.stage.Screen
import org.ttlzmc.xmc.launcher.NavigationBar
import org.ttlzmc.xmc.platform.XMCConstants
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import xmc.fluentlib.Windows
import java.io.File

class NativeNavigationBar(scene: Scene) : NavigationBar(scene) {

    init {
        Styled.registerStyled(this)
        this.widthProperty().addListener( InvalidationListener {
            val scale = Screen.getPrimary().outputScaleX.toInt()
            this.updateGrabArea(40 * scale, this.scene.width.toInt() - 200 * scale, 30 * scale)
        })
    }

    override fun onMouseClicked(event: MouseEvent) {}

    override fun onMouseDragged(event: MouseEvent) {}

    private fun updateGrabArea(startX: Int, endX: Int, height: Int) {
        val currentScale = Screen.getPrimary().outputScaleX
        Windows.setDragArea(startX, endX, height, currentScale)
    }
}