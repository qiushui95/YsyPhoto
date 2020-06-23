package son.ysy.photo.repositories

import kotlinx.coroutines.flow.flow
import me.yangcx.base.repositories.BaseRepository
import org.koin.core.inject
import son.ysy.photo.apis.LoginApi
import son.ysy.photo.data.SavedKVData
import son.ysy.photo.entities.response.ResponseLoginResult

class LoginRepository : BaseRepository() {

    private val loginApi by inject<LoginApi>()

    fun checkLogin() = flow<ResponseLoginResult> {
        val loginResult = SavedKVData.loginResult
        val loginResponseResult = loginApi.checkLogin(loginResult?.userId, loginResult?.token)
        emit(loginResponseResult)

    }.catchHttpException()

    fun login(phone: String) = flow {
        loginApi.login(phone).apply {
            emit(this)
        }
    }.catchHttpException()
}