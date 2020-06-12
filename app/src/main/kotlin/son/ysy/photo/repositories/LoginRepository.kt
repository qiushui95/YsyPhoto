package son.ysy.photo.repositories

import kotlinx.coroutines.flow.flow
import me.yangcx.base.repositories.BaseRepository
import org.koin.core.inject
import son.ysy.photo.apis.LoginApi

class LoginRepository : BaseRepository() {

    private val loginApi by inject<LoginApi>()

    fun checkLogin() = flow<Boolean> {
        val checkResult = loginApi.checkLogin().value
        emit(checkResult)
    }
}