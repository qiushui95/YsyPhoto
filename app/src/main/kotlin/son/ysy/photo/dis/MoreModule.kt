package son.ysy.photo.dis

import org.koin.dsl.module

object MoreModule {
    val instance by lazy {
        module {
//            single {
//                LoginOrRegisterRepository(get(), get())
//            }
        }
    }
}