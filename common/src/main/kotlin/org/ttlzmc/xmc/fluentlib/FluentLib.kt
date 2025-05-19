package org.ttlzmc.xmc.fluentlib

import com.sun.jna.platform.win32.WinDef.*
import com.sun.jna.platform.win32.WinReg
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary.StdCallCallback
import org.ttlzmc.xmc.fluentlib.win32.Advapi32
import org.ttlzmc.xmc.fluentlib.win32.Dwmapi
import org.ttlzmc.xmc.fluentlib.win32.User32


object FluentLib {
    private
    const val WINDOWS_11_22H2: Int = 22621
    private
    const val DWMBACKDROP_DISABLE: Int = 1
    private
    const val DWMBACKDROP_MICA: Int = 2
    private
    const val DWMBACKDROP_TABBED: Int = 4

    private
    var dragStartX: Int = 0
    private
    var dragEndX: Int = 0
    private
    var headerHeight: Int = 0
    private
    var displayScale: Double = 0.0

    private
    val originalProcs: MutableMap<HWND, WinUser.WindowProc?> = HashMap()

    fun getBuildNumber(): Int {
        val pcbData = IntByReference(4)
        val pvData = IntByReference()
        val result = Advapi32.INSTANCE.RegGetValueA(
            WinReg.HKEY_LOCAL_MACHINE,
            "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
            "CurrentBuildNumber",
            Advapi32.RRF_RT_REG_DWORD,
            null,
            pvData,
            pcbData
        )
        return if (result == 0) pvData.value else 0
    }

    fun isDarkTheme(): Boolean {
        val pvData = IntByReference()
        val pcbData = IntByReference(4)
        val result = Advapi32.INSTANCE.RegGetValueA(
            WinReg.HKEY_CURRENT_USER,
            "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
            "AppsUseLightTheme",
            Advapi32.RRF_RT_REG_DWORD,
            null,
            pvData,
            pcbData
        )
        return result == 0 && pvData.value == 0
    }

    fun setDragArea(startX: Int, endX: Int, height: Int, scale: Double) {
        dragStartX = (startX * scale).toInt()
        dragEndX = (endX * scale).toInt()
        headerHeight = (height * scale).toInt()
        displayScale = scale
    }

    fun setMica(hWnd: HWND?, useMica: Boolean) {
        val backdrop = if (useMica)
            DWMBACKDROP_MICA
        else
            DWMBACKDROP_DISABLE
        val ref = IntByReference(backdrop)
        Dwmapi.INSTANCE.DwmSetWindowAttribute(hWnd, Dwmapi.DWMWA_SYSTEMBACKDROP_TYPE, ref, 4)
    }

    fun subclassWindow(title: String?, useMica: Boolean, useHeaderBar: Boolean) {
        val hWnd = User32.INSTANCE.FindWindowW(null, title) ?: return

        setMica(hWnd, useMica)
        setHeaderBar(hWnd, useHeaderBar)
    }

    fun setHeaderBar(hWnd: HWND, useHeaderBar: Boolean) {
        if (useHeaderBar) {
            val proc: WinUser.WindowProc = CustomWndProc()
            val orig: WinUser.WindowProc? = User32.INSTANCE.SetWindowLongPtrW(
                hWnd,
                User32.GWLP_WNDPROC,
                proc
            ) as WinUser.WindowProc?
            originalProcs[hWnd] = orig
            User32.INSTANCE.SetWindowPos(
                hWnd,
                null,
                0, 0, 0, 0,
                User32.SWP_FRAMECHANGED or User32.SWP_NOMOVE or
                        User32.SWP_NOSIZE or User32.SWP_NOZORDER
            )
        } else {
            val orig: WinUser.WindowProc? = originalProcs[hWnd]
            if (orig != null) {
                User32.INSTANCE.SetWindowLongPtrW(hWnd, User32.GWLP_WNDPROC, orig)
                User32.INSTANCE.SetWindowPos(
                    hWnd,
                    null,
                    0, 0, 0, 0,
                    User32.SWP_FRAMECHANGED or User32.SWP_NOMOVE or
                            User32.SWP_NOSIZE or User32.SWP_NOZORDER
                )
            }
        }
    }

    private
    class CustomWndProc : WinUser.WindowProc {
        override fun callback(hWnd: HWND?, uMsg: Int, wParam: WPARAM?, lParam: LPARAM?): LRESULT? {
            return User32.INSTANCE.DefWindowProcW(hWnd, uMsg, wParam, lParam)
        }
    }
}