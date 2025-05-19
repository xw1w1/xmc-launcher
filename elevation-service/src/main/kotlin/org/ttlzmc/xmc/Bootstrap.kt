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
    val parsedRunArgs = ArgumentParser.parse(args)
    val context = when (parsedRunArgs.getArgument("launch-type")) {
        "launcher" -> Context.LAUNCHER
        "installation" -> Context.INSTALLER
        "uninstallation" -> Context.UNINSTALLER
        else -> throw IllegalArgumentException("Unknown or null launch type.")
    }
    Bootstrap.main(parsedRunArgs, context)
}

object Bootstrap {

    fun main(args: ArgumentParser.ParsedRunArgs, context: Context) {
        when (context) {
            Context.LAUNCHER -> {
                AssetDownloader.checkUpdates()
                runLauncher(args)
            }
            Context.INSTALLER -> Application.launch(Installer::class.java)
            Context.UNINSTALLER -> Application.launch(Uninstaller::class.java)
        }
    }

    @Suppress("unchecked_cast")
    private fun runLauncher(args: ArgumentParser.ParsedRunArgs) {
        I18n.loadTranslations()
        ThemeManager.initialize()

        System.setProperty("javafx.animation.fullspeed", "true")
        //System.setProperty("prism.lcdtext", "false")
        System.setProperty("prism.forceUploadingPainter", "true")

        var launcherClassName: Class<PlatformApplication>? = null
        if (Platform.isWindows()) {
            System.loadLibrary("FluentLib")
            launcherClassName = WindowsApplication::class.java as Class<PlatformApplication>
        } else if (Platform.isLinux()) {
            launcherClassName = LinuxApplication::class.java as Class<PlatformApplication>
        }
        Application.launch(launcherClassName)
    }
}