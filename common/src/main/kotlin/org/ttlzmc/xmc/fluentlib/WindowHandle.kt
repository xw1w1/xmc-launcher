package org.ttlzmc.xmc.fluentlib

import com.sun.jna.Platform
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.W32Errors
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.util.UUID
import kotlin.math.max
import kotlin.math.min

/**
 * A wrapper and relevant methods for a Win32 HWND.
 */
class WindowHandle(private val stage: Stage) {
    private var hwnd: WinDef.HWND? = null

    fun expandToTitle() {
        return Windows.setHeaderBar(stage.title, true)
    }

//    fun toggleMicaEffect(state: Boolean) {
//        //this.dwmSetBooleanValue(DwmAttribute.DWMWA_USE_IMMERSIVE_DARK_MODE, state)
//        if (!this.dwmSetIntValue(
//                DwmAttribute.DWMWA_SYSTEMBACKDROP_TYPE,
//                DwmAttribute.DWMSBT_MAINWINDOW.value
//            )) {
//            //this.dwmSetBooleanValue(DwmAttribute.DWMWA_MICA_EFFECT, state)
//        }
//    }

    /**
     * A wrapper for DwmSetWindowAttribute.
     *
     * @param attribute dwAttribute
     * @param value     pvAttribute
     */
//    private fun dwmSetBooleanValue(attribute: DwmAttribute, value: Boolean): Boolean {
//        return isOk(
//            DwmSupport.INSTANCE.DwmSetWindowAttribute(
//                hwnd,
//                attribute.value,
//                WinDef.BOOLByReference(WinDef.BOOL(value)),
//                WinDef.BOOL.SIZE
//            )
//        )
//    }

    /**
     * A wrapper for DwmSetWindowAttribute.
     *
     * @param attribute dwAttribute
     * @param value     pvAttribute
     * @return True if it was successful, false if it wasn't.
     */
//    private fun dwmSetIntValue(attribute: DwmAttribute, value: Int): Boolean {
//        return isOk(
//            DwmSupport.INSTANCE.DwmSetWindowAttribute(
//                hwnd,
//                attribute.value,
//                WinDef.DWORDByReference(WinDef.DWORD(value.toLong())),
//                WinDef.DWORD.SIZE
//            )
//        )
//    }

    init {
        if (Platform.getOSType() != Platform.WINDOWS && stage.titleProperty().isBound) {
            throw UnsupportedOperationException("Current platform is not supported")
        }

        val stageTitle = stage.title
        val tempTitle = stageTitle + UUID.randomUUID()
        stage.title = tempTitle
        this.hwnd = User32.INSTANCE.FindWindow(null, tempTitle)
        stage.title = stageTitle

        if (hwnd == null) throw NullPointerException("Can't find window handle for stage: $stage")
    }

    companion object {
        fun isOk(result: WinNT.HRESULT?): Boolean {
            return WinNT.HRESULT.compare(result, W32Errors.S_OK) == 0
        }

        fun rgb(color: Color): Int {
            return ((floatingTo8Bit(color.blue) shl 16)
                    or (floatingTo8Bit(color.green) shl 8)
                    or floatingTo8Bit(color.red))
        }

        fun floatingTo8Bit(n: Double): Int {
            return min(255.0, max(n * 255.0, 0.0)).toInt()
        }
    }
}