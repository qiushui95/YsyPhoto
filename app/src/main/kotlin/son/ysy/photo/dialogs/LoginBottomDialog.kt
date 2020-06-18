package son.ysy.photo.dialogs

import android.content.Context
import com.blankj.utilcode.util.*
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_bottom_login.view.*
import me.yangcx.base.ext.click
import son.ysy.photo.R

class LoginBottomDialog : BottomPopupView {
    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        continueBlock: (phone: String) -> Unit
    ) : super(context) {
        this.continueBlock = continueBlock
    }

    private var continueBlock: ((phone: String) -> Unit)? = null

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_bottom_login
    }

    override fun onCreate() {
        super.onCreate()
        btnDialogBottomLoginCancel.click {
            dismiss()
        }
        btnDialogBottomLoginContinue.click {
            val phoneInput = etDialogBottomLoginPhone.text.toString()
            if (!RegexUtils.isMobileExact(phoneInput)) {
                ToastUtils.showShort(R.string.string_dialog_bottom_login_phone_format_error)
            } else {
                dismissWith {
                    continueBlock?.invoke(phoneInput)
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