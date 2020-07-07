package son.ysy.photo

import android.os.Bundle
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_home.*
import son.ysy.lib.base.activity.BaseActivity
import son.ysy.lib.common.AppRouter

@RouterAnno(
    path = AppRouter.Home.PATH_INDEX
)
class HomeActivity : BaseActivity(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnHome.setOnClickListener {

        }
    }
}