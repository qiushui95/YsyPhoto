package son.ysy.photo.ui

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.Utils
import me.yangcx.base.activities.BaseActivity
import me.yangcx.base.annotations.BindLayoutRes
import son.ysy.photo.R

@BindLayoutRes(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        Utils.init(application)
    }
}