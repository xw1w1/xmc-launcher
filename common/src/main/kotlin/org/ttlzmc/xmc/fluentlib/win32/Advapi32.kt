package org.ttlzmc.xmc.fluentlib.win32

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinReg.HKEY
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary

@Suppress("MemberVisibilityCanBePrivate", "FunctionName", "unused")
interface Advapi32 : StdCallLibrary {
    fun RegGetValueA(
        hkey: HKEY?,
        lpSubKey: String?,
        lpValue: String?,
        dwFlags: Int,
        pdwType: IntByReference?,
        pvData: IntByReference?,
        pcbData: IntByReference?
    ): Int

    companion object {
        val INSTANCE: Advapi32 = Native.load("advapi32", Advapi32::class.java)

        const val RRF_RT_REG_DWORD: Int = 0x00000010
    }
}