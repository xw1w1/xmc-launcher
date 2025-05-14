package org.ttlzmc.xmc.platform

import java.io.File

object XMCConstants {
    val LAUNCHER_HOME_DIRECTORY = File(System.getProperty("user.home"), "xmc")
    val LAUNCHER_ASSETS_DIRECTORY = File(LAUNCHER_HOME_DIRECTORY, "assets")
    val LAUNCHER_CONFIGS_DIRECTORY = File(LAUNCHER_HOME_DIRECTORY, "config")
    val NAMESPACE = "xmc"
}