package son.ysy.photo.dialogs

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_bottom_login.view.*
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.yangcx.base.ext.click
import me.yangcx.base.others.appViewModel
import son.ysy.photo.R
import son.ysy.photo.ext.getShowMessage
import son.ysy.photo.viewmodels.LoginGlobalViewModel

class LoginBottomDialog(context: Context) : BottomPopupView(context) {
    private val lottieJob by lazy {
        SupervisorJob()
    }
    private val lifecycleOwner by lazy {
        context as LifecycleOwner
    }

    private val viewModel by appViewModel<LoginGlobalViewModel>()

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_bottom_login
    }

    override fun onCreate() {
        super.onCreate()
        btnDialogBottomLoginCancel.click {
            dismiss()
        }
        btnDialogBottomLoginContinue.click {
            KeyboardUtils.hideSoftInput(etDialogBottomLoginPhone)
            val phoneInput = etDialogBottomLoginPhone.text.toString()
            if (!RegexUtils.isMobileExact(phoneInput)) {
                ToastUtils.showShort(R.string.string_dialog_bottom_login_phone_format_error)
            } else {
                viewModel.loginIn(phoneInput)
            }
        }
        viewModel.loginDelegate
            .busyStateLive
            .observe(lifecycleOwner) {
                if (it) {
                    lifecycleOwner.lifecycleScope.launchWhenResumed {
                        launch(lottieJob) {
                            delay(500)
                            groupDialogBottomLoginButton.visibility = View.INVISIBLE
                            lottieDialogBottomLogining.visibility = View.VISIBLE
                            lottieDialogBottomLogining.playAnimation()
                        }
                    }
                } else {
                    lottieJob.cancelChildren()
                    groupDialogBottomLoginButton.visibility = View.VISIBLE
                    lottieDialogBottomLogining.visibility = View.INVISIBLE
                    lottieDialogBottomLogining.cancelAnimation()
                }
            }
        lifecycleOwner.lifecycleScope
            .launchWhenResumed {
                launch {
                    viewModel.loginDelegate
                        .dataFlow
                        .collect {
                            dismiss()
                        }
                }
                launch {
                    viewModel.loginDelegate
                        .errorFlow
                        .collect {
                            ToastUtils.showShort(it.getShowMessage())
                        }
                }
            }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lottieDialogBottomLogin.playAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lottieDialogBottomLogin.cancelAnimation()
    }
}