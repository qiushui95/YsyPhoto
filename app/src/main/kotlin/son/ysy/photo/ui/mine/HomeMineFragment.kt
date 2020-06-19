package son.ysy.photo.ui.mine

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_home_mine.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.ext.merge
import me.yangcx.base.ext.observeViewLifecycle
import me.yangcx.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import son.ysy.photo.R
import son.ysy.photo.data.LoginStatusData
import son.ysy.photo.exceptions.ResponseTokenException
import son.ysy.photo.ext.getShowMessage
import son.ysy.photo.ui.MainActivity
import son.ysy.photo.ui.items.itemErrorFullView
import son.ysy.photo.ui.items.itemLoadingFullView
import son.ysy.photo.ui.items.itemNotLoginFullView

@BindLayoutRes(R.layout.fragment_home_mine)
class HomeMineFragment : BaseFragment() {
    private val viewModel by stateViewModel<HomeMineViewModel>()

    private val modelBuildJob by lazy {
        SupervisorJob()
    }

    private lateinit var loginStatusObserver: Observer<Boolean>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        LoginStatusData.busyState
            .observeViewLifecycle(this) {
                dealLoginBusy(it)
            }
    }

    private fun dealLoginBusy(loginBusy: Boolean) {
        if (loginBusy) {
            if (this::loginStatusObserver.isInitialized) {
                LoginStatusData.loginStatus.removeObserver(loginStatusObserver)
            }
            rvHomeMine.withModels {
                itemLoadingFullView {
                    id(0)
                    loadingMessage(R.string.string_common_loading)
                }
            }
        } else {
            rvHomeMine.clear()
            val error = LoginStatusData.getError()
            if (error != null) {
                rvHomeMine.withModels {
                    if (error is ResponseTokenException) {
                        itemNotLoginFullView {
                            id(0)
                            loginTip(error.getShowMessage())
                            loginClick {
                                (activity as? MainActivity)?.showLoginDialog()
                            }
                        }
                    } else {
                        itemErrorFullView {
                            id(0)
                            errorMessage(error.getShowMessage())
                            retryClick {
                                lifecycleScope.launchWhenResumed {
                                    LoginStatusData.checkLoginResult()
                                }
                            }
                        }
                    }
                }
            } else {
                loginStatusObserver = LoginStatusData.loginStatus
                    .observeViewLifecycle(this) {
                        dealLoginStatus(it)
                    }
            }
        }
    }

    private fun dealLoginStatus(loginStatus: Boolean) {
        if (!loginStatus) {
            rvHomeMine.withModels {
                itemNotLoginFullView {
                    id(0)
                    loginClick {
                        (activity as? MainActivity)?.showLoginDialog()
                    }
                }
            }
        } else {
            rvHomeMine.clear()
        }
    }
}