package son.ysy.photo.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import me.yangcx.base.activities.BaseActivity
import me.yangcx.base.annotations.BindLayoutRes
import son.ysy.photo.R
import son.ysy.photo.data.LoginStatusData

@BindLayoutRes(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        LoginStatusData.loginResultChecking
            .observe(this) {

            }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            LoginStatusData.checkLoginResult()
        }
    }
}