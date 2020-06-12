package son.ysy.photo.https

import okhttp3.Interceptor
import okhttp3.Response
import son.ysy.photo.data.LoginStatusData

class CommonRequestHeaderInterceptor : Interceptor {
    companion object {
        private const val KEY_USER_ID = "userId"
        private const val KEY_TOKEN = "token"
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val builder = oldRequest.newBuilder()

        val loginResult = LoginStatusData.loginResult
        if (loginResult != null) {
            builder.addHeader(KEY_USER_ID, loginResult.userId)
            builder.addHeader(KEY_TOKEN, loginResult.token)
        }
        return chain.proceed(builder.build())
    }
}