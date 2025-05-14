package org.ttlzmc.xmc.fluentlib.win32

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary

@Suppress("MemberVisibilityCanBePrivate", "FunctionName", "unused")
interface Dwmapi : StdCallLibrary {
    fun DwmSetWindowAttribute(hwnd: HWND?, dwAttribute: Int, pvAttribute: IntByReference?, cbAttribute: Int): Int
    fun DwmIsCompositionEnabled(pfEnabled: IntByReference?): Int

    companion object {
        val INSTANCE: Dwmapi = Native.load("dwmapi", Dwmapi::class.java)

        const val DWMWA_SYSTEMBACKDROP_TYPE: Int = 38
    }
}