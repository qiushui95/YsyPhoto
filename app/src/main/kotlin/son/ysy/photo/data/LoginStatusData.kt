package son.ysy.photo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.blankj.utilcode.util.LogUtils
import com.squareup.moshi.Moshi
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import retrofit2.HttpException
import son.ysy.photo.entities.response.ResponseErrorInfo
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.exceptions.ResponseException
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
                .catch {
                    LogUtils.w(it.message)
                }
                .onStart {
                    loginResultCheckingMutable.postValue(true)
                }.onCompletion {
                    loginResultCheckingMutable.postValue(false)
                }.flowOn(Dispatchers.IO)
                .collect {
                    loginStatusMutable.postValue(it)
                }
        }
    }
}