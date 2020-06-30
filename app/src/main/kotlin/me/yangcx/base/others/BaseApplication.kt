package me.yangcx.base.others

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.blankj.utilcode.util.Utils
import com.effective.android.anchors.*
import me.yangcx.base.exceptions.UndefineInitTaskException
import me.yangcx.base.https.DefaultOkHttpConfig
import me.yangcx.base.init.SpiderManInitTask
import me.yangcx.base.init.StethoInitTask
import me.yangcx.base.inits.AndroidUtilCodeInitTask
import me.yangcx.base.inits.KoinInitTask
import me.yangcx.base.inits.MmkvInitTask
import me.yangcx.base.json.DefaultJsonConfig
import org.koin.core.module.Module
import son.ysy.photo.BuildConfig
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

inline fun <reified VM : ViewModel> appViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null
) = lazy {
    getAppViewModel<VM>(viewModelFactory)
}

inline fun <reified VM : ViewModel> getAppViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null
) = (Utils.getApp() as BaseApplication).getAppViewModel(VM::class, viewModelFactory)

abstract class BaseApplication : Application(),
    TaskCreator,
    Application.ActivityLifecycleCallbacks {

    protected open val jsonConfig = DefaultJsonConfig()

    protected open val okHttpConfig = DefaultOkHttpConfig()

    protected abstract val apiModule: Module

    protected abstract val repositoryModule: Module

    protected abstract val viewModelModule: Module

    protected open val moreModule: Array<Module> = emptyArray()

    private val viewModelStore = ViewModelStore()

    private val viewModelFactory by lazy {
        ViewModelProvider.NewInstanceFactory()
    }

    private val activeActivityCount = AtomicInteger(0)

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        runInitProject()
        registerActivityLifecycleCallbacks(this)
    }

    private fun runInitProject() {
        AnchorsManager.getInstance()
            .debuggable {
                BuildConfig.DEBUG
            }.taskFactory {
                Project.TaskFactory(this)
            }.anchors(this::createAnchors)
            .graphics(this::createGraphics)
            .startUp()
    }

    final override fun createTask(taskName: String): Task = when (taskName) {
        AndroidUtilCodeInitTask.TASK_NAME -> AndroidUtilCodeInitTask(this)
        KoinInitTask.TASK_NAME -> KoinInitTask(
            this,
            jsonConfig,
            okHttpConfig,
            apiModule,
            repositoryModule,
            viewModelModule,
            moreModule
        )
        MmkvInitTask.TASK_NAME -> MmkvInitTask(this)
        SpiderManInitTask.TASK_NAME -> SpiderManInitTask(this)
        StethoInitTask.TASK_NAME -> StethoInitTask(this)
        else -> createMoreInitTask(taskName)
    }

    protected open fun createMoreInitTask(taskName: String): Task {
        throw UndefineInitTaskException(taskName)
    }

    protected open fun createAnchors(): Array<String> = arrayOf(KoinInitTask.TASK_NAME)

    protected open fun createGraphics(): Array<String> = arrayOf(
        AndroidUtilCodeInitTask.TASK_NAME,
        MmkvInitTask.TASK_NAME.sons(KoinInitTask.TASK_NAME),
        SpiderManInitTask.TASK_NAME,
        StethoInitTask.TASK_NAME
    )

    fun <VM : ViewModel> getAppViewModel(
        viewModelClass: KClass<VM>,
        viewModelFactory: ViewModelProvider.Factory?
    ): VM {
        return ViewModelProvider(
            { viewModelStore },
            viewModelFactory ?: this.viewModelFactory
        )[viewModelClass.java]
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activeActivityCount.incrementAndGet()
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activeActivityCount.decrementAndGet() == 0) {
            viewModelStore.clear()
        }
    }

}