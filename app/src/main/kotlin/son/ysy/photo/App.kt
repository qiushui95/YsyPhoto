package son.ysy.photo

import android.app.Application
import android.util.Log
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.Config
import com.xiaojinzi.component.impl.application.ModuleManager
import son.ysy.lib.common.AppRouter

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Component.init(
            BuildConfig.DEBUG,
            Config.with(this)
                .defaultScheme(AppRouter.SCHEME)
                // 使用内置的路由重复检查的拦截器, 如果为 true,
                // 那么当两个相同的路由发生在指定的时间内后一个路由就会被拦截
                .useRouteRepeatCheckInterceptor(true)
                // 1000 是默认的, 表示相同路由拦截的时间间隔
                .routeRepeatCheckDuration(1000)
                // 是否打印日志提醒你哪些路由使用了 Application 为 Context 进行跳转
                .tipWhenUseApplication(true)
                // 这里表示使用 ASM 字节码技术加载模块, 默认是 false
                // 如果是 true 请务必配套使用 Gradle 插件, 下一步就是可选的配置 Gradle 插件
                // 如果是 false 请直接略过下一步 Gradle 的配置
                .optimizeInit(false)
                // 自动加载所有模块, 打开此开关后下面无需手动注册了
                // 但是这个依赖 optimizeInit(true) 才会生效
                .autoRegisterModule(false)
                .build()
        )
        ModuleManager.getInstance().registerArr("home")
    }
}