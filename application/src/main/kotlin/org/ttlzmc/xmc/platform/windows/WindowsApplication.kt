package org.ttlzmc.xmc.platform.windows

import javafx.geometry.Insets
import javafx.scene.effect.InnerShadow
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.ttlzmc.xmc.launcher.PlatformApplication
import org.ttlzmc.xmc.platform.XMCConstants
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration
import xmc.fluentlib.dwm.WindowHandle
import java.io.File

class WindowsApplication : PlatformApplication() {

    private var nativeHandle: WindowHandle? = null

    override fun onStageCreated(stage: Stage) {
        instance = this
        Styled.registerStyled(this)
        stage.apply {
            scene = rootScene
            navigationBar = NativeNavigationBar(rootScene)
            root.children.add(navigationBar)
            initStyle(StageStyle.UNIFIED)
            requestFocus()
            show()
        }
        nativeHandle = WindowHandle(rootStage)
    }

    override fun applyNativeDecorations(stage: Stage) {
        nativeHandle?.setHeaderBar(true)
    }

    override fun onMaximizedStateChanges(fs: Boolean) {
        navigationBar.onMaximized(fs)
        attachElementsToAnchor(fs)
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        nativeHandle?.apply {
            this.setDarkMode(configuration.isDarkTheme)
            if (configuration.useMica) {
                this.setMica(true)
                this.resetCaptionColor()
            } else {
                this.setMica(false)
                this.setCaptionColor(configuration.headerFillColor)
            }
        }

        pageRoot.apply {
            background =
                Background(
                    BackgroundFill(
                        configuration.backgroundSubColor,
                        CornerRadii(15.0, 0.0, 0.0, 0.0, false),
                        Insets.EMPTY
                    )
                )
            effect = InnerShadow(10.0, configuration.shadowColor)
        }
        rootScene.root.stylesheets.clear()
        rootScene.root.stylesheets.add(
            File(XMCConstants.LAUNCHER_HOME_DIRECTORY, configuration.cssFilePath).toURI().toString()
        )
    }

}