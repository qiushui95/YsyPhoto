package son.ysy.photo.https

import okhttp3.Interceptor
import okhttp3.Response
import son.ysy.photo.BuildConfig
import son.ysy.photo.data.LoginStatusData

class CommonRequestHeaderInterceptor : Interceptor {
    companion object {
        const val KEY_USER_ID = "userId"
        const val KEY_TOKEN = "token"
        private const val KEY_DEBUG = "isDebug"
        private const val KEY_VERSION_CODE = "versionCode"
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val builder = oldRequest.newBuilder()

        val loginResult = LoginStatusData.loginCheckDelegate.getCurrentData()

        if (loginResult != null) {
            builder.addHeader(KEY_USER_ID, loginResult.userId)
            builder.addHeader(KEY_TOKEN, loginResult.token)
        }
        builder.addHeader(KEY_DEBUG, BuildConfig.DEBUG.toString())
        builder.addHeader(KEY_VERSION_CODE, BuildConfig.VERSION_CODE.toString())
        return chain.proceed(builder.build())
    }
}