package son.ysy.photo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.blankj.utilcode.util.LogUtils
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

    private val busyStateMutable by lazy {
        MutableLiveData<Boolean>()
    }

    val busyState: LiveData<Boolean> = busyStateMutable.distinctUntilChanged()

    private val loginStatusMutable by lazy {
        MutableLiveData(false)
    }

    val loginStatus: LiveData<Boolean> = loginStatusMutable.distinctUntilChanged()

    private var error: Throwable? = null

    fun getError() = error

    var loginResult: ResponseLoginResult? = MMKV.defaultMMKV()
        .decodeParcelable(KEY_LOGIN_RESULT, ResponseLoginResult::class.java)
        private set(value) {
            val mmkv = MMKV.defaultMMKV()
            if (value == null) {
                mmkv.reKey(KEY_LOGIN_RESULT)
            } else {
                mmkv.encode(KEY_LOGIN_RESULT, value)
            }
            field = value
        }

    suspend fun loginIn(phone: String) {
        loginRepository.login(phone)
            .onStart {
                busyStateMutable.postValue(true)
            }.onCompletion {
                busyStateMutable.postValue(false)
            }.catch {
                error = it
            }
            .flowOn(Dispatchers.IO)
            .collect {
                error = null
                loginStatusMutable.postValue(true)
                this.loginResult = loginResult
            }
    }

    fun logout() {
        loginStatusMutable.postValue(false)
        this.loginResult = null
    }

    suspend fun checkLoginResult() = coroutineScope<Unit> {
        parentJob.children
            .forEach {
                it.cancelAndJoin()
            }
        launch(Dispatchers.IO + parentJob) {
            loginRepository.checkLogin()
                .catch {
                    error = it
                }
                .onStart {
                    busyStateMutable.postValue(true)
                }.onCompletion {
                    busyStateMutable.postValue(false)
                }.flowOn(Dispatchers.IO)
                .collect {
                    error = null
                    loginStatusMutable.postValue(it)
                }
        }
    }
}