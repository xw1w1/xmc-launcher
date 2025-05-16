package org.ttlzmc.xmc.launcher

import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.ParallelTransition
import javafx.animation.ScaleTransition
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

import javafx.util.Duration
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration

abstract class Modal(width: Double, height: Double) : AnchorPane(), Styled {
    private val backdrop = Rectangle().apply {
        fill = Color.rgb(0, 0, 0, 0.5)
        widthProperty().bind(this@Modal.prefWidthProperty())
        heightProperty().bind(this@Modal.prefHeightProperty())
    }

    private val contentContainer = VBox(20.0).apply {
        alignment = Pos.TOP_CENTER
        maxWidthProperty().set(width)
        maxHeightProperty().set(height)
        padding = Insets(5.0)
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        this.contentContainer.style = """
            -fx-background-color: #161616;
            -fx-background-radius: 15;
            -fx-border-color: #404040;
            -fx-border-radius: 15;
            -fx-effect: dropshadow(gaussian, rgba(10,10,10,10.5), 25, 0.2, 0, 5);
        """.trimIndent()
    }

    init {
        val stage = PlatformApplication.rootStage

        prefWidthProperty().bind(stage.widthProperty())
        prefHeightProperty().bind(stage.heightProperty())

        contentContainer.alignment = Pos.CENTER

        children.addAll(backdrop, contentContainer)
        isVisible = false

        sceneProperty().addListener { _, _, newScene ->
            newScene?.setOnKeyPressed { event ->
                if (event.code == KeyCode.ESCAPE) close()
            }
        }
    }

    protected fun createHeader(title: String) {
        val header = VBox().apply {
            alignment = Pos.TOP_CENTER
            padding = Insets(0.0, 0.0, 0.0, 0.0)

            val topBar = StackPane().apply {
                alignment = Pos.TOP_LEFT
                padding = Insets(0.0)

                children.add(
                    Button("\uD83E\uDC60").apply {
                        font = Font.font(16.0)
                        prefWidth = 35.0
                        prefHeight = 35.0
                        setOnAction { close() }
                    }
                )
            }

            val modTitle = Label(title).apply {
                font = Font.font("Segoe UI Semibold", FontWeight.SEMI_BOLD, 20.0)
                textFill = Color.WHITE
                alignment = Pos.CENTER
                padding = Insets(10.0, 0.0, 0.0, 0.0)
            }

            children.addAll(topBar, modTitle)
        }
        this.children.add(header)
        setTopAnchor(header, 0.0)
    }

    fun open() {
        isVisible = true
        toFront()

        val fadeInBackdrop = FadeTransition(Duration.millis(250.0), backdrop).apply {
            fromValue = 0.0
            toValue = 1.0
            interpolator = Interpolator.EASE_OUT
        }

        val scaleUp = ScaleTransition(Duration.millis(300.0), contentContainer).apply {
            fromX = 0.97
            fromY = 0.97
            toX = 1.0
            toY = 1.0
            interpolator = Interpolator.EASE_OUT
        }

        val fadeInContent = FadeTransition(Duration.millis(250.0), contentContainer).apply {
            fromValue = 0.0
            toValue = 1.0
            interpolator = Interpolator.EASE_OUT
        }

        ParallelTransition(fadeInBackdrop, scaleUp, fadeInContent).play()
    }

    fun close() {
        ParallelTransition(
            FadeTransition(Duration.millis(250.0), contentContainer).apply {
                toValue = 0.0
                interpolator = Interpolator.EASE_OUT
            },
            ScaleTransition(Duration.millis(200.0), contentContainer).apply {
                toX = 0.97
                toY = 0.97
                interpolator = Interpolator.EASE_OUT
            },
            FadeTransition(Duration.millis(300.0), backdrop).apply {
                toValue = 0.0
                interpolator = Interpolator.EASE_OUT
            }
        ).apply {
            setOnFinished {
                isVisible = false
            }
            play()
        }
    }
}