package org.ttlzmc.xmc.fluentlib.win32

import com.sun.jna.Native
import com.sun.jna.platform.win32.BaseTSD.LONG_PTR
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.*
import com.sun.jna.win32.StdCallLibrary
import com.sun.jna.win32.StdCallLibrary.StdCallCallback
import com.sun.jna.win32.W32APIOptions

@Suppress("MemberVisibilityCanBePrivate", "FunctionName", "unused")
interface User32 : StdCallLibrary {
    fun FindWindowW(lpClassName: String?, lpWindowName: String?): HWND?
    fun SetWindowLongPtrW(hWnd: HWND?, nIndex: Int, callback: StdCallCallback?): LONG_PTR?
    fun SetWindowPos(hWnd: HWND?, hWndInsertAfter: HWND?, X: Int, Y: Int, cx: Int, cy: Int, uFlags: Int): Boolean
    fun GetWindowRect(hWnd: HWND?, rect: WinDef.RECT?): Boolean
    fun DefWindowProcW(hWnd: HWND?, uMsg: Int, wParam: WPARAM?, lParam: LPARAM?): LRESULT?

    companion object {
        val INSTANCE: User32 = Native.load(
            "user32",
            User32::class.java, W32APIOptions.DEFAULT_OPTIONS
        )

        const val GWLP_WNDPROC: Int = -4
        const val SWP_NOSIZE: Int = 0x0001
        const val SWP_NOMOVE: Int = 0x0002
        const val SWP_NOZORDER: Int = 0x0004
        const val SWP_FRAMECHANGED: Int = 0x0020
    }
}