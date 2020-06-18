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
import son.ysy.photo.ui.items.ItemLoginCheckingViewModel_
import son.ysy.photo.ui.items.itemLoginCheckingView
import son.ysy.photo.ui.items.itemNotLoginView

@BindLayoutRes(R.layout.fragment_home_mine)
class HomeMineFragment : BaseFragment() {
    private val viewModel by stateViewModel<HomeMineViewModel>()

    private val modelBuildJob by lazy {
        SupervisorJob()
    }

    private lateinit var loginStatusObserver: Observer<Boolean>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvHomeMine.withModels {
            itemNotLoginView {
                id(javaClass.name)
            }
        }
        bindViewModel()
    }

    private fun bindViewModel() {
        LoginStatusData.loginResultChecking
            .observeViewLifecycle(this) { checking ->
                if (checking) {
                    if (this::loginStatusObserver.isInitialized) {
                        LoginStatusData.loginStatus.removeObserver(loginStatusObserver)
                    }
                    rvHomeMine.withModels {
                        itemLoginCheckingView {
                            id(0)
                        }
                    }
                } else {
                    rvHomeMine.clear()
                    loginStatusObserver = LoginStatusData.loginStatus
                        .observeViewLifecycle(this) { loginStatus ->
                            if (loginStatus) {
                                rvHomeMine.clear()
                            } else {
                                rvHomeMine.withModels {
                                    itemNotLoginView {
                                        id(0)
                                    }
                                }
                            }
                        }
                }
            }
    }
}