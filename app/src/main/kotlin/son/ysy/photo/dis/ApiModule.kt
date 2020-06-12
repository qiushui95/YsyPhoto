package son.ysy.photo.dis

import org.koin.dsl.module
import retrofit2.Retrofit
import son.ysy.photo.apis.LoginApi

object ApiModule {
    val instance by lazy {
        module {
            single {
                get<Retrofit>().create(LoginApi::class.java)
            }
        }
    }
}