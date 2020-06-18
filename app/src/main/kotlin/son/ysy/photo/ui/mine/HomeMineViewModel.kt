package son.ysy.photo.ui.mine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import me.yangcx.base.viewmodels.BaseViewModel
import me.yangcx.base.viewmodels.delegates.RequestDelegateVMSimple
import me.yangcx.base.viewmodels.impls.RequestDelegateVMImplSimple
import org.koin.core.inject
import son.ysy.photo.entities.response.ResponseUserInfo
import son.ysy.photo.repositories.UserInfoRepository

class HomeMineViewModel(handle: SavedStateHandle) : BaseViewModel() {

    private val userInfoRepository by inject<UserInfoRepository>()

    val userInfoGetDelegate: RequestDelegateVMSimple<ResponseUserInfo> by lazy {
        RequestDelegateVMImplSimple<ResponseUserInfo>(
            handle,
            viewModelScope,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true
        )
    }

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        userInfoGetDelegate.doRequestSimple {
            userInfoRepository.getUserInfo()
        }
    }
}