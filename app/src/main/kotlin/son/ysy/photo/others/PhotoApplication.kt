package son.ysy.photo.others

import son.ysy.photo.dis.ViewModelModule
import me.yangcx.base.https.DefaultOkHttpConfig
import me.yangcx.base.others.BaseApplication
import org.koin.core.module.Module
import son.ysy.photo.dis.ApiModule
import son.ysy.photo.dis.MoreModule
import son.ysy.photo.dis.RepositoryModule
import son.ysy.photo.https.PhotoOkHttpConfig

@Suppress("unused")
class PhotoApplication : BaseApplication() {
    override val okHttpConfig: DefaultOkHttpConfig
        get() = PhotoOkHttpConfig()

    override val apiModule: Module
        get() = ApiModule.instance

    override val repositoryModule: Module
        get() = RepositoryModule.instance

    override val viewModelModule: Module
        get() = ViewModelModule.instance

    override val moreModule: Array<Module>
        get() = arrayOf(MoreModule.instance)
}