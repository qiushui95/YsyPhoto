package me.yangcx.base.utils

import androidx.annotation.ColorInt
import dev.jorgecastillo.androidcolorx.library.isDark

object ColorUtil {
    fun isDarkColor(@ColorInt color: Int): Boolean {
        return color.isDark()
    }
}