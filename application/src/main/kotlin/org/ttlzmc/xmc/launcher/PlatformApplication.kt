package org.ttlzmc.xmc.launcher

import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.ttlzmc.xmc.ArgumentParser
import org.ttlzmc.xmc.launcher.page.WelcomePage
import org.ttlzmc.xmc.themes.beans.Styled

abstract class PlatformApplication : Application(), Styled {

    override fun start(stage: Stage) {
        rootStage = stage
        stage.title = "xmc Launcher"
        this.onStageCreated(stage)
        this.openPage(WelcomePage)
        attachElementsToAnchor(false)
        rootStage.maximizedProperty().addListener { _, _, new ->
            this.onMaximizedStateChanges(new)
        }
    }

    abstract fun onStageCreated(stage: Stage)
    abstract fun onMaximizedStateChanges(fs: Boolean)

    fun openModal(modal: Modal) {
        root.children.add(modal)
        modal.open()
    }

    fun closeModal(modal: Modal) {
        modal.close()
        root.children.remove(modal)
    }

    fun openPage(page: UIPage) {
        pageRoot.children.clear()
        pageRoot.children.add(page)
    }

    companion object {
        lateinit var rootStage: Stage
        lateinit var arguments: ArgumentParser.ParsedRunArgs
        lateinit var navigationBar: NavigationBar

        val pageRoot: HBox = HBox().apply {
            alignment = Pos.TOP_LEFT
            padding = Insets(20.0)
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        val root: AnchorPane = AnchorPane().apply {
            background =
                Background(BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))
            children.add(pageRoot)
        }

        val rootScene: Scene = Scene(root, 800.0, 500.0).also { it.fill = Color.TRANSPARENT }

        fun attachElementsToAnchor(fs: Boolean) {
            AnchorPane.setTopAnchor(pageRoot, if (fs) 35.0 else 30.0)
            AnchorPane.setLeftAnchor(pageRoot, 60.0)
            AnchorPane.setBottomAnchor(pageRoot, 0.0)
            AnchorPane.setRightAnchor(pageRoot, 0.0)
        }
    }
}