package me.yangcx.base.inits

import android.app.Application
import com.effective.android.anchors.Task
import me.yangcx.base.dis.HttpModule
import me.yangcx.base.dis.JsonModule
import me.yangcx.base.https.DefaultOkHttpConfig
import me.yangcx.base.json.DefaultJsonConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class KoinInitTask(
    private val application: Application,
    private val jsonConfig: DefaultJsonConfig,
    private val okHttpConfig: DefaultOkHttpConfig,
    private val apiModule: Module,
    private val repositoryModule: Module,
    private val viewModelModule: Module,
    private val moreModule: Array<Module>
) : Task(TASK_NAME, false) {
    companion object {
        const val TASK_NAME = "koin"
    }

    override fun run(name: String) {
        startKoin {
            androidContext(application)
            printLogger(Level.ERROR)
            modules(
                JsonModule(jsonConfig).instance,
                HttpModule(okHttpConfig).instance,
                apiModule,
                repositoryModule,
                viewModelModule,
                *moreModule
            )
        }
    }
}