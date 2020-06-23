package son.ysy.photo.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.BarUtils
import com.lxj.xpopup.XPopup
import me.yangcx.base.activities.BaseActivity
import me.yangcx.base.annotations.BindLayoutRes
import son.ysy.photo.R
import son.ysy.photo.data.LoginStatusData
import son.ysy.photo.dialogs.LoginBottomDialog

@BindLayoutRes(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private val loginDialog by lazy {
        XPopup.Builder(this)
            .asCustom(LoginBottomDialog(this))
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        lifecycleScope.launchWhenResumed {
            LoginStatusData.checkLoginResult()
        }
    }


    fun showLoginDialog() {
        loginDialog.show()
    }
}