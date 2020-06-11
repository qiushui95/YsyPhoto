package me.yangcx.base.annotations

import androidx.annotation.LayoutRes
import me.yangcx.base.exceptions.LayoutException
import java.lang.annotation.Inherited

/**
 * 绑定布局文件注解
 * create by 97457
 * create at 2018/12/13
 */
@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindLayoutRes(@LayoutRes val layoutRes: Int) {
    companion object {
        inline fun <reified T> getLayoutRes(
            clz: Class<T>
        ) = clz.getAnnotation(BindLayoutRes::class.java)
            ?.layoutRes
            ?.takeIf {
                it != 0
            } ?: throw LayoutException(this::class)
    }
}