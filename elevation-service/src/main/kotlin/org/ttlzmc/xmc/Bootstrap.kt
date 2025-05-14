package org.ttlzmc.xmc

import com.sun.jna.Platform
import javafx.application.Application
import org.ttlzmc.xmc.installer.Installer
import org.ttlzmc.xmc.launcher.PlatformApplication
import org.ttlzmc.xmc.launcher.ThemeManager
import org.ttlzmc.xmc.platform.linux.LinuxApplication
import org.ttlzmc.xmc.platform.windows.WindowsApplication
import org.ttlzmc.xmc.translations.I18n
import org.ttlzmc.xmc.uninstaller.Uninstaller

fun main(args: Array<String>) {
    Bootstrap.main(ArgumentParser.parse(args), Context.LAUNCHER)
}

object Bootstrap {

    fun main(args: ArgumentParser.ParsedRunArgs, context: Context) {
        when (context) {
            Context.LAUNCHER -> {
                AssetDownloader.downloadAssets()
                proceedLauncher()
            }
            Context.INSTALLER -> Application.launch(Installer::class.java)
            Context.UNINSTALLER -> Application.launch(Uninstaller::class.java)
        }
    }

    private fun proceedLauncher() {
        I18n.loadTranslations()
        ThemeManager.initialize()
        System.setProperty("javafx.animation.fullspeed", "true")
        var launcherClassName: Class<PlatformApplication>? = null
        if (Platform.isWindows()) {
            System.loadLibrary("FluentLib")
            System.setProperty("prism.lcdtext", "false")
            System.setProperty("prism.forceUploadingPainter", "true")
            launcherClassName = WindowsApplication::class.java as Class<PlatformApplication>
        } else if (Platform.isLinux()) {
            launcherClassName = LinuxApplication::class.java as Class<PlatformApplication>
        }
        Application.launch(launcherClassName)
    }
}