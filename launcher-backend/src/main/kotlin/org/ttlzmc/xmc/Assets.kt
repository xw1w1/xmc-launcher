package org.ttlzmc.xmc

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.ttlzmc.xmc.platform.XMCConstants
import java.io.File

object Assets {

    fun getIconResource(): ImageView? {
        val iconFile = File(File(XMCConstants.LAUNCHER_ASSETS_DIRECTORY, "images"), "appicon.png")
        return if (iconFile.exists()) ImageView(Image(iconFile.inputStream())) else null
    }

}