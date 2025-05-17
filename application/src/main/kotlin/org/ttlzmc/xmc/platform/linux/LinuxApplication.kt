package org.ttlzmc.xmc.platform.linux

import javafx.geometry.Insets
import javafx.scene.effect.InnerShadow
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.ttlzmc.xmc.launcher.PlatformApplication
import org.ttlzmc.xmc.themes.beans.Styled
import org.ttlzmc.xmc.themes.beans.ThemeConfiguration

class LinuxApplication : PlatformApplication() {

    override fun onStageCreated(stage: Stage) {
        Styled.registerStyled(this)
        stage.apply {
            scene = rootScene
            navigationBar = DraggableNavigationBar(rootScene)
            root.children.add(navigationBar)
            initStyle(StageStyle.UNIFIED)
            requestFocus()
            show()
        }
    }

    override fun onMaximizedStateChanges(fs: Boolean) {
        navigationBar.onMaximized(fs)
        attachElementsToAnchor(fs)
    }

    override fun applyStyle(configuration: ThemeConfiguration) {
        pageRoot.apply {
            background =
                Background(BackgroundFill(configuration.backgroundSubColor, CornerRadii(15.0, 0.0, 0.0, 0.0, false), Insets.EMPTY))
            effect = InnerShadow(10.0, configuration.shadowColor)
        }
    }
}