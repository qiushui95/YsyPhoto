package son.ysy.photo

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.Utils
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.activity_main.*
import son.ysy.lib.base.activity.BaseActivity
import son.ysy.lib.base.annotation.BindLayoutRes
import son.ysy.lib.base.ext.click
import son.ysy.lib.common.AppRouter
import kotlin.random.Random

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnMain.click {
            Router.with()
                .url(AppRouter.Home.Index.url)
                .forward()
        }
    }
}