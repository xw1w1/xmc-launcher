package org.ttlzmc.xmc.launcher

import javafx.geometry.Insets
import javafx.scene.layout.VBox
import org.ttlzmc.xmc.themes.beans.Styled

abstract class UIPage : VBox(), Styled {

    init {
        padding = Insets(PAGE_PADDING)
        this.createContents()
    }

    abstract fun createContents()

    companion object {
        const val PAGE_SPACING = 20.0
        const val PAGE_PADDING = 25.0
    }
}