package org.ttlzmc.xmc.platform.windows

import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.ttlzmc.xmc.launcher.PlatformApplication
import org.ttlzmc.xmc.launcher.ThemeManager
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import xmc.fluentlib.dwm.WindowHandle

class WindowsApplication : PlatformApplication(), Styled {

    override fun onStageCreated(stage: Stage) {
        Styled.registerStyled(this)
        stage.apply {
            title = "fluentapp"
            scene = rootScene
            root.children.add(NativeNavigationBar(rootScene))
            initStyle(StageStyle.UNIFIED)
            requestFocus()
            show()
        }.also { Styled.notifyThemeChanged(ThemeManager.getLauncherTheme()) }
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
    }

}