package son.ysy.photo.https

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import son.ysy.photo.data.LoginStatusData

class ResponseErrorInterceptor : Interceptor, KoinComponent {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).apply {
            when (code) {
                in intArrayOf(700, 701) -> {
                    LoginStatusData.logout()
                }
            }
        }
    }
}