package org.ttlzmc.xmc.platform.linux

import javafx.scene.Scene
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import org.ttlzmc.xmc.launcher.NavigationBar

class DraggableNavigationBar(scene: Scene) : NavigationBar(scene, null) {

    init {
        this.setOnMousePressed {
            this.offsetXProperty.set(it.sceneX)
            this.offsetYProperty.set(it.sceneY)
        }

        this.setOnMouseClicked(::onMouseClicked)
        this.setOnMouseDragged(::onMouseDragged)
    }

    override fun onMouseClicked(event: MouseEvent) {
        if ((event.button == MouseButton.PRIMARY) && (event.clickCount == 2)) {
            val stage = scene.window as Stage
            val maximized = stage.isMaximized
            stage.isMaximized = !maximized
        }
    }

    override fun onMouseDragged(event: MouseEvent) {
        val stage = scene.window as Stage
        if (stage.isMaximized) stage.isMaximized = false
        val scale = stage.outputScaleX
        stage.x = (event.screenX - this.offsetXProperty().get() * scale)
        stage.y = (event.screenY - this.offsetYProperty().get() * scale)
    }
}