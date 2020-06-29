package me.yangcx.base.utils

import android.graphics.Color
import androidx.annotation.ColorInt

object ColorUtil {
    fun isDarkColor(@ColorInt color: Int): Boolean {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return red * 0.299 + green * 0.587 + blue * 0.114 < 192
    }
}