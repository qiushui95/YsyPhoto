package son.ysy.photo.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.BarUtils
import com.lxj.xpopup.XPopup
import me.yangcx.base.activities.BaseActivity
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.others.appViewModel
import son.ysy.photo.R
import son.ysy.photo.dialogs.LoginBottomDialog
import son.ysy.photo.viewmodels.LoginGlobalViewModel

@BindLayoutRes(R.layout.activity_main)
class MainActivity : BaseActivity() {
    private val viewModel by appViewModel<LoginGlobalViewModel>()

    private val loginDialog by lazy {
        XPopup.Builder(this)
            .asCustom(LoginBottomDialog(this))
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        lifecycleScope.launchWhenResumed {
            viewModel.checkLoginResult()
        }
    }


    fun showLoginDialog() {
        loginDialog.show()
    }
}