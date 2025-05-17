package org.ttlzmc.xmc.platform.windows

import javafx.geometry.Insets
import javafx.scene.effect.InnerShadow
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.ttlzmc.xmc.launcher.PlatformApplication
import org.ttlzmc.xmc.launcher.ThemeManager
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import xmc.fluentlib.dwm.WindowHandle

class WindowsApplication : PlatformApplication() {

    override fun onStageCreated(stage: Stage) {
        Styled.registerStyled(this)
        stage.apply {
            scene = rootScene
            navigationBar = NativeNavigationBar(rootScene)
            root.children.add(navigationBar)
            initStyle(StageStyle.UNIFIED)
            requestFocus()
            show()
        }.also { Styled.notifyThemeChanged(ThemeManager.getLauncherTheme()) }
    }

    override fun onMaximizedStateChanges(fs: Boolean) {
        navigationBar.onMaximized(fs)
        attachElementsToAnchor(fs)
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        val handle = WindowHandle(rootStage)
        handle.setHeaderBar(true)
        handle.setDarkMode(configuration.isDarkTheme)
        if (configuration.useMica) {
            handle.setMica(true)
            rootScene.fill = Color.TRANSPARENT
        } else {
            handle.setCaptionColor(configuration.backgroundFillColor)
        }

        pageRoot.apply {
            background =
                Background(BackgroundFill(configuration.backgroundSubColor, CornerRadii(15.0, 0.0, 0.0, 0.0, false), Insets.EMPTY))
            effect = InnerShadow(10.0, configuration.shadowColor)
        }
    }

}