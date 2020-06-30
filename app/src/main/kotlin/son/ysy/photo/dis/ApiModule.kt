package son.ysy.photo.dis

import org.koin.dsl.module
import retrofit2.Retrofit
import son.ysy.photo.apis.LoginApi
import son.ysy.photo.apis.UploadApi
import son.ysy.photo.apis.UserInfoApi

object ApiModule {
    val instance by lazy {
        module {
            single {
                get<Retrofit>().create(LoginApi::class.java)
            }
            single {
                get<Retrofit>().create(UserInfoApi::class.java)
            }
            single {
                get<Retrofit>().create(UploadApi::class.java)
            }
        }
    }
}