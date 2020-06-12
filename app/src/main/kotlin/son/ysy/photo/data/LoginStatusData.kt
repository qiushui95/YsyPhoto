package son.ysy.photo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.repositories.LoginRepository

object LoginStatusData : KoinComponent {
    private const val KEY_LOGIN_RESULT = "loginResult"

    private val parentJob = SupervisorJob()

    private val loginRepository by inject<LoginRepository>()

    private val loginStatusMutable by lazy {
        MutableLiveData(false)
    }

    val loginStatus: LiveData<Boolean> = loginStatusMutable.distinctUntilChanged()

    private val loginResultCheckingMutable by lazy {
        MutableLiveData<Boolean>()
    }

    val loginResultChecking: LiveData<Boolean> = loginResultCheckingMutable.distinctUntilChanged()

    var loginResult: ResponseLoginResult? =
        MMKV.defaultMMKV().decodeParcelable(KEY_LOGIN_RESULT, ResponseLoginResult::class.java)
        private set(value) {
            MMKV.defaultMMKV().encode(KEY_LOGIN_RESULT, value)
            field = value
        }

    fun loginIn(loginResult: ResponseLoginResult) {
        loginStatusMutable.postValue(true)
        this.loginResult = loginResult
    }

    fun logout() {
        loginStatusMutable.postValue(false)
        this.loginResult = null
    }

    suspend fun checkLoginResult() = coroutineScope {
        parentJob.children
            .forEach {
                it.cancelAndJoin()
            }
        launch(Dispatchers.IO + parentJob) {
            loginRepository.checkLogin()
                .onStart {
                    loginResultCheckingMutable.postValue(true)
                }
                .catch {
it.printStackTrace()
                }.onCompletion {
                    loginResultCheckingMutable.postValue(false)
                }.flowOn(Dispatchers.IO)
                .collect {
                    loginStatusMutable.postValue(it)
                }
        }
    }
}