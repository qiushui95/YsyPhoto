package son.ysy.photo.https

import me.yangcx.base.https.DefaultOkHttpConfig
import okhttp3.OkHttpClient

class PhotoOkHttpConfig : DefaultOkHttpConfig() {
    override fun configClient(builder: OkHttpClient.Builder) {
        builder.addInterceptor(CommonRequestHeaderInterceptor())
            .addNetworkInterceptor(ResponseErrorInterceptor())
    }
}