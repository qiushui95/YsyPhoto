package son.ysy.photo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import me.yangcx.base.viewmodels.impls.RequestDelegateVMImpl
import org.koin.core.KoinComponent
import org.koin.core.inject
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.repositories.LoginRepository

object LoginStatusData : KoinComponent {

    private val parentJob = SupervisorJob()

    private val loginRepository by inject<LoginRepository>()

    val loginCheckDelegate by lazy {
        RequestDelegateVMImpl<ResponseLoginResult>(
            null,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true,
            requestParentJob = parentJob
        )
    }

    val loginDelegate by lazy {
        RequestDelegateVMImpl<ResponseLoginResult>(
            null,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true,
            requestParentJob = parentJob
        )
    }

    suspend fun checkLoginResult() {
        loginCheckDelegate.doRequest {
            loginRepository.checkLogin()
        }
    }

    suspend fun loginIn(phone: String) {
        loginDelegate.doRequest {
            loginRepository.login(phone)
                .onEach {
                    loginCheckDelegate.doChangeData(it)
                    SavedKVData.loginResult = it
                }.flowOn(Dispatchers.Main)
        }
    }

    fun logout() {
        SavedKVData.loginResult=null
    }
}