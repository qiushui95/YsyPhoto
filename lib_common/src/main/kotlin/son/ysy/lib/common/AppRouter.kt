package son.ysy.lib.common

sealed class AppRouter(val host: String, val path: String) {
    companion object {
        const val SCHEME = "photos"
        const val HOST_HOME = "home"
    }

    val url: String
        get() = "$SCHEME://$host/$path"

    sealed class Home(path: String) : AppRouter(HOST_HOME, path) {
        companion object {
            const val PATH_INDEX = "index"
        }

        object Index : Home(PATH_INDEX)
    }
}