package me.yangcx.base.json

import me.yangcx.base.parcelables.ParcelableBooleanJsonAdapter

open class DefaultJsonConfig {
    fun getJsonAdapters() = arrayOf(
        ParcelableBooleanJsonAdapter(),
        *getMoreJsonAdapter()
    )


    protected open fun getMoreJsonAdapter(): Array<Any> = emptyArray()
}