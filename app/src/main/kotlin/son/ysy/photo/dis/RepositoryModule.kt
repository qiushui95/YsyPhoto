package son.ysy.photo.dis

import org.koin.dsl.module
import son.ysy.photo.repositories.LoginRepository

object RepositoryModule {
    val instance by lazy {
        module {
            single {
                LoginRepository()
            }
        }
    }
}