package org.ttlzmc.xmc.launcher

import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox

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
    rootScene: Scene,
    val appIcon: ImageView? = null,
) : HBox() {
    val offsetXProperty = ReadOnlyDoubleWrapper(0.0)
    val offsetYProperty = ReadOnlyDoubleWrapper(0.0)

    init {
        this.alignment = Pos.TOP_RIGHT
        this.appIcon?.apply {
            fitWidth = 50.0
            fitHeight = 50.0
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

    abstract fun onMouseClicked(event: MouseEvent)

    abstract fun onMouseDragged(event: MouseEvent)

    companion object {
        const val SPACING = 5.0
        val PADDING = Insets(12.0, 15.0, 10.0, 10.0)
        const val INITIAL_PREF_HEIGHT = 60.0
    }
}