package me.yangcx.base.ext

import android.view.View
import com.blankj.utilcode.util.ClickUtils

fun View.clickWithView(vararg otherView: View, duration: Int = 1000, block: ((View) -> Unit)?) {
    val views = arrayOf(this, *otherView)
    if (block == null) {
        views.forEach {
            it.setOnClickListener(null)
        }
    } else {
        val listener = View.OnClickListener(block)
        ClickUtils.applySingleDebouncing(views, duration.toLong(), listener)
    }
}

fun View.click(vararg otherView: View, duration: Int = 1000, block: (() -> Unit)?) {
    val views = arrayOf(this, *otherView)
    if (block == null) {
        views.forEach {
            it.setOnClickListener(null)
        }
    } else {
        val listener = View.OnClickListener {
            block()
        }
        ClickUtils.applySingleDebouncing(views, duration.toLong(), listener)
    }
}

fun View.click(vararg otherView: View, duration: Int = 1000, listener: View.OnClickListener?) {
    val views = arrayOf(this, *otherView)
    if (listener == null) {
        views.forEach {
            it.setOnClickListener(null)
        }
    } else {
        ClickUtils.applySingleDebouncing(views, duration.toLong(), listener)
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}