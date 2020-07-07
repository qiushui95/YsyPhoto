package son.ysy.photo

import android.app.Application
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.xiaojinzi.component.anno.ModuleAppAnno
import com.xiaojinzi.component.application.IComponentApplication

@ModuleAppAnno
class Application : IComponentApplication {
    override fun onCreate(app: Application) {
        Thread.sleep(2000)
    }

    override fun onDestroy() {

    }
}