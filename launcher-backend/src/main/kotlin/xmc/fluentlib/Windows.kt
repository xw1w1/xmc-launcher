package xmc.fluentlib

object Windows {
    external fun buildnumber(): Int
    external fun subclass(title: String, useMica: Boolean, useHeaderBar: Boolean)

    private external fun setdragarea(startX: Int, endX: Int, height: Int, scale: Double)
    private external fun isdarkmode(): Boolean
    private external fun setheaderbar(title: String, useHeaderBar: Boolean)

    fun setDragArea(startX: Int, endX: Int, height: Int, scale: Double) = setdragarea(startX, endX, height, scale)

    fun setHeaderBar(title: String, use: Boolean) = setheaderbar(title, use)

    fun isWindowsDarkModeEnabled() = isdarkmode()
}