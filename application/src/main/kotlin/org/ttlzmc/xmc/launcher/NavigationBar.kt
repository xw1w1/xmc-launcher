package org.ttlzmc.xmc.launcher

import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import org.ttlzmc.xmc.Assets
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

    val appIcon = Assets.getIconResource()

    init {
        this.alignment = Pos.CENTER_LEFT
        this.appIcon?.apply {
            fitWidth = 40.0
            fitHeight = 40.0
            pickOnBoundsProperty().set(true)
            preserveRatioProperty().set(true)
            this@NavigationBar.children.add(this)
        }
        this.padding = PADDING
        this.spacing = SPACING

        this.prefHeight = INITIAL_PREF_HEIGHT
        this.prefWidthProperty().bind(rootScene.widthProperty())
    }

    fun offsetXProperty(): ReadOnlyDoubleProperty {
        return this.offsetXProperty.readOnlyProperty
    }

    fun offsetYProperty(): ReadOnlyDoubleProperty {
        return this.offsetYProperty.readOnlyProperty
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        this.stylesheets.clear()
        this.styleClass.clear()
        this.stylesheets.add(File(XMCConstants.LAUNCHER_HOME_DIRECTORY, configuration.cssFilePath).toURI().toString())
        this.styleClass.add("native-navbar")
    }

    abstract fun onMouseClicked(event: MouseEvent)

    abstract fun onMouseDragged(event: MouseEvent)

    companion object {
        const val SPACING = 5.0
        val PADDING = Insets(0.0, 0.0, 0.0, 10.0)
        const val INITIAL_PREF_HEIGHT = 60.0
    }
}