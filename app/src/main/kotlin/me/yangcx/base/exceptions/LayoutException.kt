package me.yangcx.base.exceptions

import kotlin.reflect.KClass

class LayoutException(
    kClass: KClass<*>
) : RuntimeException("请绑定布局-->${kClass.qualifiedName}"), MessageThrowable