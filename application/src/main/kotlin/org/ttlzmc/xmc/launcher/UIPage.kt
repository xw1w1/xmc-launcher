package org.ttlzmc.xmc.launcher

import javafx.geometry.Insets
import javafx.scene.layout.VBox
import org.ttlzmc.xmc.themes.beans.Styled

/**
 * Abstract base class for content pages displayed in the main application area of [PlatformApplication].
 *
 * This [VBox]-based container provides standardized spacing and padding for content pages,
 * automatically occupying all available space not reserved for control panels.
 *
 * Implementation notes:
 * 1. Inheritors must override [createContents] to define page-specific UI components
 * 2. Use singleton `object` declaration for pages without initialization requirements
 * 3. Use class declaration with initialization logic in `init` block when resource loading is needed
 * 4. Built-in styling follows application-wide [Styled] theming constraints
 *
 * Page layout structure:
 * ```
 * ┌──────────────────────────────────────────────────────┐
 * │                 (Control panels)                     │
 * │Xpx                     Xpx                           │
 * │   ┌────────────────────────────────────────────────┐←┐
 * │   │                  Page Content                  │ │
 * │   │             (UIPage implementation)            │ │
 * │   │                                                │ │
 * │   └────────────────────────────────────────────────┘ │
 * └──────────────────────────────────────────────────────┘
 * ```
 * The implementation ensures consistent spacing and automatic content positioning while
 * preserving responsive layout characteristics.
 */
abstract class UIPage : VBox(), Styled {

    init {
        padding = Insets(PAGE_PADDING)
        this.createContents()
    }

    abstract fun createContents()

    companion object {
        const val PAGE_PADDING = 25.0
    }
}