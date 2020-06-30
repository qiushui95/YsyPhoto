package son.ysy.photo.ui.mine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import me.yangcx.base.viewmodels.BaseViewModel
import me.yangcx.base.viewmodels.delegates.RequestDelegateSimpleVM
import me.yangcx.base.viewmodels.impls.RequestDelegateSimpleVMImpl
import org.koin.core.inject
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.entities.response.ResponseUserInfo
import son.ysy.photo.repositories.UserInfoRepository

class HomeMineViewModel(handle: SavedStateHandle) : BaseViewModel() {

    private val userInfoRepository by inject<UserInfoRepository>()

    val userInfoGetDelegate: RequestDelegateSimpleVM<ResponseUserInfo> by lazy {
        RequestDelegateSimpleVMImpl<ResponseUserInfo>(
            handle,
            viewModelScope
        )
    }

    private var loginResult: ResponseLoginResult? = null

    private fun getUserInfo() {
        userInfoGetDelegate.doRequestSimple {
            userInfoRepository.getUserInfo()
        }
    }

    fun getUserInfo(loginResult: ResponseLoginResult) {
        if (this.loginResult != loginResult) {
            this.loginResult = loginResult
            getUserInfo()
        }
    }

    fun refreshData() {
        getUserInfo()
    }

    fun clearData() {

    }
}