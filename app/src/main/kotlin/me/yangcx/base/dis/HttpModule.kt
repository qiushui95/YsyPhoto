package me.yangcx.base.dis

import me.yangcx.base.https.DefaultOkHttpConfig
import me.yangcx.base.init.configDebug
import me.yangcx.base.others.UsedConstants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class HttpModule(okHttpConfig: DefaultOkHttpConfig) {
    val instance by lazy {
        module {
            single {
                OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(UsedConstants.HTTP_TIME_OUT * 1L, TimeUnit.MILLISECONDS)
                    .readTimeout(UsedConstants.HTTP_TIME_OUT * 1L, TimeUnit.MILLISECONDS)
                    .writeTimeout(UsedConstants.HTTP_TIME_OUT * 1L, TimeUnit.MILLISECONDS)
                    .apply {
                        okHttpConfig.configClient(this)
                    }
                    .configDebug(get())
                    .build()
            }

            single {
                Retrofit.Builder()
                    .baseUrl("http://192.168.31.16")
                    .addConverterFactory(MoshiConverterFactory.create(get()))
                    .client(get())
                    .build()
            }
        }
    }
}