package son.ysy.lib.base.ext

inline fun <reified DATA : Any> List<DATA>.toArray() = Array(size) {
    get(it)
}

inline fun <reified DATA : Any> DATA.toList() = listOf(this)