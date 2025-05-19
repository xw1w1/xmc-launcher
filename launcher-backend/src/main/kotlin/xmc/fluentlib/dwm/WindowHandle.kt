package xmc.fluentlib.dwm

import com.sun.jna.Platform
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.W32Errors
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinNT
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.ttlzmc.xmc.fluentlib.FluentLib
import xmc.fluentlib.Windows
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * A wrapper and relevant methods for a Win32 HWND.
 * Use the [tryFind][.tryFind] static method to acquire.
 */
@Suppress("unused")
class WindowHandle(private var stage: Stage) {

    private var hwnd: HWND = lookup(stage)

    fun getHandle() = hwnd
    fun getJavaFXStage() = stage

    fun setHeaderBar(boolean: Boolean) {
        Windows.setHeaderBar(stage.title, boolean)
    }

    fun setDarkMode(boolean: Boolean) {
        this.dwmSetBooleanValue(DwmAttribute.DWMWA_USE_IMMERSIVE_DARK_MODE, boolean)
    }

    fun setMica(boolean: Boolean) {
        FluentLib.setMica(hwnd, boolean)
    }

    fun setWindowDragArea(startX: Int, endX: Int, height: Int) {
        FluentLib.setDragArea(startX, endX, height, 1.0)
    }

    /**
     * Sets the border color.
     *
     * @param color Border color
     * @return True if it was successful, false if it wasn't.
     */
    fun setBorderColor(color: Color): Boolean {
        return dwmSetIntValue(DwmAttribute.DWMWA_BORDER_COLOR, rgb(color))
    }

    /**
     * Sets the border color.
     *
     * @param color Border color
     * @return This
     */
    fun withBorderColor(color: Color): WindowHandle {
        setBorderColor(color)
        return this
    }

    /**
     * Sets the title bar background color.
     *
     * @param color Caption color
     * @return True if it was successful, false if it wasn't.
     */
    fun setCaptionColor(color: Color): Boolean {
        return dwmSetIntValue(DwmAttribute.DWMWA_CAPTION_COLOR, rgb(color))
    }

    fun resetCaptionColor(): Boolean {
        return dwmSetIntValue(DwmAttribute.DWMWA_CAPTION_COLOR, DwmAttribute.DWMWA_CAPTION_COLOR_DEFAULT.value)
    }

    /**
     * Sets the title bar background color.
     *
     * @param color Caption color
     * @return This.
     */
    fun withCaptionColor(color: Color): WindowHandle {
        setCaptionColor(color)
        return this
    }

    /**
     * Sets the title text color.
     *
     * @param color Caption color
     * @return True if it was successful, false if it wasn't.
     */
    fun setTextColor(color: Color): Boolean {
        return dwmSetIntValue(DwmAttribute.DWMWA_TEXT_COLOR, rgb(color))
    }

    /**
     * Sets the title text color.
     *
     * @param color Caption color
     * @return This.
     */
    fun withTextColor(color: Color): WindowHandle {
        setTextColor(color)
        return this
    }

    /**
     * A wrapper for DwmSetWindowAttribute.
     *
     * @param attribute dwAttribute
     * @param value     pvAttribute
     * @return True if it was successful, false if it wasn't.
     */
    fun dwmSetBooleanValue(attribute: DwmAttribute, value: Boolean): Boolean {
        return isOk(
            DwmSupport.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                attribute.value,
                WinDef.BOOLByReference(WinDef.BOOL(value)),
                WinDef.BOOL.SIZE
            )
        )
    }

    /**
     * A wrapper for DwmSetWindowAttribute.
     *
     * @param attribute dwAttribute
     * @param value     pvAttribute
     * @return True if it was successful, false if it wasn't.
     */
    fun dwmSetIntValue(attribute: DwmAttribute, value: Int): Boolean {
        return isOk(
            DwmSupport.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                attribute.value,
                WinDef.DWORDByReference(WinDef.DWORD(value.toLong())),
                WinDef.DWORD.SIZE
            )
        )
    }

    companion object {
        /**
         * Try to acquire Win32 window handle.
         *
         * @param stage The top level window to search
         * @return Handle to the top level window
         * @throws HwndLookupException When the platform is not supported, titleProperty
         * is bound, or the window was not found.
         * @see HwndLookupException.Error
         */
        @Throws(HwndLookupException::class)
        fun lookup(stage: Stage): HWND {
            if (Platform.getOSType() != Platform.WINDOWS) {
                throw HwndLookupException(HwndLookupException.Error.NOT_SUPPORTED)
            }

            if (stage.titleProperty().isBound) {
                throw HwndLookupException(HwndLookupException.Error.BOUND)
            }

            val searchString = "stage_" + UUID.randomUUID()
            val title = stage.title
            stage.title = searchString
            val hwnd = User32.INSTANCE.FindWindow(null, searchString)
            stage.title = title
            if (hwnd != null) {
                return hwnd
            }

            throw HwndLookupException(HwndLookupException.Error.NOT_FOUND)
        }

        private fun floatingTo8Bit(n: Double): Int {
            return min(255.0, max(n * 255.0, 0.0)).toInt()
        }

        private fun isOk(result: WinNT.HRESULT?): Boolean {
            return WinNT.HRESULT.compare(result, W32Errors.S_OK) == 0
        }

        private fun rgb(color: Color): Int {
            return ((floatingTo8Bit(color.blue) shl 16)
                    or (floatingTo8Bit(color.green) shl 8)
                    or floatingTo8Bit(color.red))
        }
    }
}