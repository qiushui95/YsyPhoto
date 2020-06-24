package me.yangcx.base.ext

inline fun <reified DATA : Any> List<DATA>.toArray() = Array(size) {
    get(it)
}