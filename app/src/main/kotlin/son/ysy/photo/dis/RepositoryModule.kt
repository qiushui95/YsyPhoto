package son.ysy.photo.dis

import org.koin.dsl.module
import son.ysy.photo.repositories.LocalImageRepository
import son.ysy.photo.repositories.LoginRepository
import son.ysy.photo.repositories.UploadRepository
import son.ysy.photo.repositories.UserInfoRepository

object RepositoryModule {
    val instance by lazy {
        module {
            single {
                LoginRepository()
            }
            single {
                UserInfoRepository()
            }
            single {
                LocalImageRepository()
            }
            single {
                UploadRepository()
            }
        }
    }
}