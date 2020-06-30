package son.ysy.photo.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import me.yangcx.base.viewmodels.BaseViewModel
import me.yangcx.base.viewmodels.impls.RequestDelegateVMImpl
import org.koin.core.inject
import son.ysy.photo.data.SavedKVData
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.repositories.LoginRepository

class LoginGlobalViewModel : BaseViewModel() {

    private val parentJob = SupervisorJob()

    private val loginRepository by inject<LoginRepository>()

    val loginCheckDelegate by lazy {
        RequestDelegateVMImpl<ResponseLoginResult>(
            null,
            cancelCurrentIfBusy = false,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true,
            requestParentJob = parentJob
        )
    }

    val loginDelegate by lazy {
        RequestDelegateVMImpl<ResponseLoginResult>(
            null,
            cancelCurrentIfBusy = false,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true,
            requestParentJob = parentJob
        )
    }

    fun checkLoginResult() {
        loginCheckDelegate.doRequest(viewModelScope) {
            loginRepository.checkLogin()
        }
    }

    fun loginIn(phone: String) {
        loginDelegate.doRequest(viewModelScope) {
            loginRepository.login(phone)
                .onEach {
                    loginCheckDelegate.doChangeData(it)
                    SavedKVData.loginResult = it
                }.flowOn(Dispatchers.Main)
        }
    }

    fun logout() {
        SavedKVData.loginResult = null
    }
}