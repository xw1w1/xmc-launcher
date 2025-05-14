package org.ttlzmc.xmc.installer

import javafx.application.Application
import javafx.stage.Stage
import java.io.File

class Installer : Application() {
    override fun start(stage: Stage) {
        checkFolders()
    }

    fun checkFolders(): Boolean {
        val launcherFolder = File(System.getProperty("user.home"), "xmc")
        if (!launcherFolder.exists()) { launcherFolder.createNewFile() }
        return true
    }
}