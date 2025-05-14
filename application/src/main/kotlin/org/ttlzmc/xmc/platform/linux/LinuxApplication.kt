package org.ttlzmc.xmc.platform.linux

import javafx.stage.Stage
import javafx.stage.StageStyle
import org.ttlzmc.xmc.launcher.PlatformApplication

class LinuxApplication : PlatformApplication() {

    override fun onStageCreated(stage: Stage) {
        stage.title = "fluentapp"
        stage.initStyle(StageStyle.UNIFIED)
        stage.scene = rootScene
        root.children.add(DraggableNavigationBar(rootScene))
        stage.requestFocus()
        stage.show()
    }
}