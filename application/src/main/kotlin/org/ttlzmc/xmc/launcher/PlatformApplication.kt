package org.ttlzmc.xmc.launcher

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Stage

abstract class PlatformApplication : Application() {

    override fun start(stage: Stage) {
        rootStage = stage
        stage.title = "fluentapp"
        this.onStageCreated(stage)
    }

    abstract fun onStageCreated(stage: Stage)

    companion object {
        lateinit var rootStage: Stage

        val root: AnchorPane = AnchorPane()

        val rootScene: Scene = Scene(root, 800.0, 500.0).also { it.fill = Color.TRANSPARENT }
    }
}