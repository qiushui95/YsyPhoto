package son.ysy.photo.https

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

class ResponseErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).apply {
            this.message
            LogUtils.e(code)
        }
    }
}