package org.ttlzmc.xmc.launcher

import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Tooltip
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import org.ttlzmc.xmc.Assets
import org.ttlzmc.xmc.launcher.page.HomePage
import org.ttlzmc.xmc.platform.XMCConstants
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import java.io.File

/**
 * A custom top navigation bar implementation that extends the standard Windows window header.
 *
 * This [HBox]-based control combines native window controls with custom application-specific
 * elements in a horizontally expandable container.
 *
 * The bar dynamically adjusts its content and width to accommodate child components while
 * maintaining window decoration functionality.
 * ```
 * ╭───────────────────────────────────────────────────────────╮
 * (  xmc                  Home                       ─  □  ✖  )
 * │-----------------------------------------------------------│
 * ```
 * The implementation handles proper spacing and alignment between system controls and
 * custom elements while preserving window manager functionality.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class NavigationBar (
    rootScene: Scene
) : HBox(), Styled {
    val offsetXProperty = ReadOnlyDoubleWrapper(0.0)
    val offsetYProperty = ReadOnlyDoubleWrapper(0.0)

    val homeButton = Assets.getIconResource()?.run {
        Button().apply {
            graphic = this@run.apply {
                fitWidth = 35.0
                fitHeight = 35.0
            }

            maxWidth = 45.0
            maxHeight = 45.0
            pickOnBoundsProperty().set(true)
            preserveRatioProperty().set(true)
        }
    }

    init {
        this.alignment = Pos.CENTER_LEFT
        this.homeButton?.run {
            this@NavigationBar.children.add(this)
            this.setOnAction {
                PlatformApplication.instance.openPage(HomePage)
            }
            this.tooltip = Tooltip("Press to go Home")
        }

        this.padding = PADDING
        this.spacing = SPACING

        this.prefHeight = INITIAL_PREF_HEIGHT
        this.prefWidthProperty().bind(rootScene.widthProperty())
    }

    fun onMaximized(fs: Boolean) {
        this.padding = if (fs) PADDING_MAXIMIZED else PADDING
    }

    fun offsetXProperty(): ReadOnlyDoubleProperty {
        return this.offsetXProperty.readOnlyProperty
    }

    fun offsetYProperty(): ReadOnlyDoubleProperty {
        return this.offsetYProperty.readOnlyProperty
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        this.styleClass.add("navbar")

        this.homeButton?.apply {
            styleClass.add("home-button")
        }
    }

    abstract fun onMouseClicked(event: MouseEvent)

    abstract fun onMouseDragged(event: MouseEvent)

    companion object {
        const val SPACING = 5.0
        val PADDING = Insets(0.0, 0.0, 0.0, 7.5)
        val PADDING_MAXIMIZED = Insets(10.0, 0.0, 0.0, 7.5)
        const val INITIAL_PREF_HEIGHT = 60.0
    }
}