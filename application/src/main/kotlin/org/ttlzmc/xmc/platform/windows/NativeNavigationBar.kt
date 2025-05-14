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

class NativeNavigationBar(scene: Scene) : NavigationBar(scene, null), Styled {

    init {
        Styled.registerStyled(this)
        this.widthProperty().addListener( InvalidationListener {
            val reservedButtonArea = (((30 * 3) + (5 * 2)) * Screen.getPrimary().outputScaleX).toInt()
            this.updateGrabArea(reservedButtonArea, this.scene.width.toInt(), this.prefHeight.toInt())
        })
    }

    override fun onMouseClicked(event: MouseEvent) {}

    override fun onMouseDragged(event: MouseEvent) {}

    private fun updateGrabArea(startX: Int, endX: Int, height: Int) {
        val currentScale = Screen.getPrimary().outputScaleX
        Windows.setDragArea(startX, endX, height, currentScale)
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        this.stylesheets.clear()
        this.styleClass.clear()
        this.stylesheets.add(File(XMCConstants.LAUNCHER_HOME_DIRECTORY, configuration.cssFilePath).toURI().toString())
        this.styleClass.add("native-navbar")
    }

}