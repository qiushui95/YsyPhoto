package son.ysy.photo.ui.mine

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home_mine.*
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.entities.RequestStatus
import me.yangcx.base.ext.observeViewLifecycle
import me.yangcx.base.fragments.BaseFragment
import me.yangcx.base.others.appViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import son.ysy.photo.R
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.entities.response.ResponseUserInfo
import son.ysy.photo.entities.ui.HomeMineButtonConfig
import son.ysy.photo.exceptions.ResponseTokenException
import son.ysy.photo.ext.getShowMessage
import son.ysy.photo.ui.MainActivity
import son.ysy.photo.ui.home.HomeFragmentDirections
import son.ysy.photo.ui.items.itemErrorFullView
import son.ysy.photo.ui.items.itemLoadingFullView
import son.ysy.photo.ui.items.itemNotLoginFullView
import son.ysy.photo.viewmodels.LoginGlobalViewModel

@BindLayoutRes(R.layout.fragment_home_mine)
class HomeMineFragment : BaseFragment() {
    private val viewModel by stateViewModel<HomeMineViewModel>()

    private val loginViewModel by appViewModel<LoginGlobalViewModel>()

    private lateinit var userInfoObserver: Observer<RequestStatus>

    private val buttonConfigList by lazy {
        listOf(
            HomeMineButtonConfig(
                R.drawable.ic_home_mine_album,
                R.string.string_home_mine_button_album,
                8
            ),
            HomeMineButtonConfig(
                R.drawable.ic_home_mine_upload,
                R.string.string_home_mine_button_upload,
                1
            ),
            HomeMineButtonConfig(
                R.drawable.ic_home_mine_share,
                R.string.string_home_mine_button_share,
                16
            ),
            HomeMineButtonConfig(
                R.drawable.ic_home_mine_logout,
                R.string.string_home_mine_button_logout,
                16
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        loginViewModel.loginCheckDelegate
            .requestStatusLive
            .observeViewLifecycle(this) {
                dealCheckRequestStatus(it)
            }
    }

    private fun dealCheckRequestStatus(requestStatus: RequestStatus) {
        when (requestStatus) {
            RequestStatus.Loading -> {
                removeUserInfoObserver()
                buildLoadingItem()
            }
            is RequestStatus.Success -> {
                requestStatus.data<ResponseLoginResult>()
                    ?.also { loginResult ->
                        viewModel.getUserInfo(loginResult)
                        userInfoObserver = viewModel.userInfoGetDelegate
                            .requestStatusLive
                            .observeViewLifecycle(this) {
                                dealUserInfoRequestStatus(it)
                            }
                    }
            }
            is RequestStatus.Error -> {
                removeUserInfoObserver()
                buildErrorItem(requestStatus.error) {
                    lifecycleScope.launchWhenResumed {
                        loginViewModel.loginCheckDelegate.retry()
                    }
                }
            }
        }
    }

    private fun dealUserInfoRequestStatus(requestStatus: RequestStatus) {
        when (requestStatus) {
            RequestStatus.Loading -> buildLoadingItem()
            is RequestStatus.Success -> {
                requestStatus.data<ResponseUserInfo>()
                    ?.also { userInfo ->
                        rvHomeMine.clear()
                        rvHomeMine.withModels {
                            itemHomeMineUserInfoView {
                                id(javaClass.name, userInfo.phone)
                                avatarUrl(userInfo.avatarUrl)
                                phone(userInfo.phone)
                                relationship(userInfo.relationShip)
                            }
                            buttonConfigList.forEach { buttonConfig ->
                                itemHomeMineButtonView {
                                    id(buttonConfig.iconRes)
                                    iconRes(buttonConfig.iconRes)
                                    tipRes(buttonConfig.tipRes)
                                    marginTop(buttonConfig.marginTopDp)
                                    click { model, _, _, _ ->
                                        when (model.iconRes()) {
                                            R.drawable.ic_home_mine_upload -> {
                                                findNavController().navigate(HomeFragmentDirections.toSelectUpload())
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
            is RequestStatus.Error -> buildErrorItem(requestStatus.error) {
                lifecycleScope.launchWhenResumed {
                    viewModel.userInfoGetDelegate.retry()
                }
            }

        }
    }

    private fun buildLoadingItem() {
        rvHomeMine.clear()
        rvHomeMine.withModels {
            itemLoadingFullView {
                id(javaClass.name)
                loadingMessage(R.string.string_common_loading)
            }
        }
    }

    private fun buildErrorItem(error: Throwable, clickBlock: () -> Unit) {
        if (error is ResponseTokenException) {
            rvHomeMine.clear()
            rvHomeMine.withModels {
                itemNotLoginFullView {
                    id(javaClass.name)
                    loginTip(error.getShowMessage())
                    loginClick {
                        (activity as? MainActivity)?.showLoginDialog()
                    }
                }
            }
        } else {
            rvHomeMine.clear()
            rvHomeMine.withModels {
                itemErrorFullView {
                    id(javaClass.name)
                    errorMessage(error.getShowMessage())
                    retryClick(clickBlock)
                }
            }
        }
    }

    private fun removeUserInfoObserver() {
        if (this::userInfoObserver.isInitialized) {
            viewModel.userInfoGetDelegate
                .requestStatusLive
                .removeObserver(userInfoObserver)

        }
    }
}