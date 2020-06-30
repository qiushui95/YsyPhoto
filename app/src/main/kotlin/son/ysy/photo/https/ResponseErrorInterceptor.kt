package son.ysy.photo.https

import me.yangcx.base.others.appViewModel
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import son.ysy.photo.viewmodels.LoginGlobalViewModel

class ResponseErrorInterceptor : Interceptor, KoinComponent {
    private val viewModel by appViewModel<LoginGlobalViewModel>()

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).apply {
            when (code) {
                in intArrayOf(700, 701) -> {
                    viewModel.logout()
                }
            }
        }
    }
}