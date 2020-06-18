object Libs {
    object Build {
        const val plugin = "com.android.tools.build:gradle:4.0.0"
        const val tools = "29.0.3"
        const val min = 21
        const val target = 29
    }

    object Single {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha01"
        const val coreKtx = "androidx.core:core-ktx:1.4.0-alpha01"
        const val material = "com.google.android.material:material:1.3.0-alpha01"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"

        //drawable生成器
        const val background = "com.noober.background:core:1.6.4"

        //持久化数据
        const val mmkv = "com.tencent:mmkv-static:1.1.2"

        //内存泄露检测
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.3"

        //多功能工具包
        const val util = "com.blankj:utilcodex:1.29.0"

        //初始化
        const val init = "com.effective.android:anchors:1.1.0"

        //时间处理
        const val joda = "joda-time:joda-time:2.10.6"

        //图片选择框架
        const val imagePicker = "com.ypx.yimagepicker:androidx:3.1.4"

        //崩溃展示
        const val spiderMan = "com.simple:spiderman:1.1.6"

        //图片预览
        const val imageViewer = "com.github.iielse:imageviewer:2.0.13"

        //弹窗框架
        const val popup = "com.lxj:xpopup:2.0.2"
    }

    object Group {
        object Kotlin {
            private const val version = "1.3.72"
            const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
            const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
            const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        }

        object View {
            //viewPager2
            const val viewPager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"

            //约束布局
            const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta6"

            //RecyclerView
            const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0-alpha03"

            //圆角图片
            const val roundedImageView = "com.makeramen:roundedimageview:2.3.0"

            //底部导航
            const val bottomNavigation = "com.ashokvarma.android:bottom-navigation-bar:2.2.0"

            //动画框架
            const val lottie = "com.airbnb.android:lottie:3.4.1"

            //带加载的button
            const val loadingButton = "com.github.lihangleo2:SmartLoadingView:2.0.1"

            //下拉刷新
            const val refreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-rc01"

            //流式布局
            const val flexBox = "com.google.android:flexbox:2.0.1"

            //大图查看View
            const val bigImage = "com.davemorrissey.labs:subsampling-scale-image-view:3.10.0"

            //侧滑删除
            const val swipeView = "com.github.Tunous:SwipeActionView:1.3.0"

            //高斯模糊
            const val blur = "com.hoko:hoko-blur:1.3.4"

            //实时背景高斯模糊
            const val realTimeBlurView = "com.github.mmin18:realtimeblurview:1.2.1"
        }

        object Fragment {
            private const val version = "1.3.0-alpha05"
            const val core = "androidx.fragment:fragment:$version"
            const val ktx = "androidx.fragment:fragment-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.0-beta01"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        //依赖注入框架
        object Koin {
            private const val version = "2.1.6"
            const val scope = "org.koin:koin-androidx-scope:$version"
            const val viewModel = "org.koin:koin-androidx-viewmodel:$version"
            const val ext = "org.koin:koin-androidx-ext:$version"
        }

        //协程
        object Coroutines {
            private const val version = "1.3.7"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }

        object Lifecycle {
            private const val version = "2.3.0-alpha03"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val common = "androidx.lifecycle:lifecycle-common:$version"
            const val liveDataCore = "androidx.lifecycle:lifecycle-livedata-core-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val service = "androidx.lifecycle:lifecycle-service:$version"
        }

        //json处理
        object MoShi {
            private const val version = "1.9.2"
            const val core = "com.squareup.moshi:moshi:$version"
            const val plugin = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }

        //网络请求框架
        object Retrofit {
            private const val version = "2.9.0"
            const val core = "com.squareup.retrofit2:retrofit:$version"
            const val moshi = "com.squareup.retrofit2:converter-moshi:$version"
            const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.4.1"
            const val chucker = "com.github.ChuckerTeam.Chucker:library:3.0.1"
        }

        object OkHttp {
            private const val version = "4.7.2"
            const val core = "com.squareup.okhttp3:okhttp:$version"
            const val mock = "com.squareup.okhttp3:mockwebserver:$version"
        }

        //开发检测
        object Stetho {
            private const val version = "1.5.1"
            const val core = "com.facebook.stetho:stetho:$version"
            const val okHttp = "com.facebook.stetho:stetho-okhttp3:$version"
        }

        //图片加载
        object Coil {
            const val core = "io.coil-kt:coil:0.11.0"
        }

        //RecyclerView Adapter便捷库
        object Epoxy {
            private const val version = "3.11.0"
            const val core = "com.airbnb.android:epoxy:$version"
            const val processor = "com.airbnb.android:epoxy-processor:$version"
            const val databinding = "com.airbnb.android:epoxy-databinding:$version"
            const val glide = "com.airbnb.android:epoxy-glide-preloading:$version"
            const val paging = "com.airbnb.android:epoxy-paging:$version"
        }

        //View Style 操作库
        object Paris {
            private const val version = "1.5.0"
            const val core = "com.airbnb.android:paris:$version"
            const val plugin = "com.airbnb.android:paris-processor:$version"
        }

        //room数据库
        object Room {
            private const val version = "2.2.5"
            const val core = "androidx.room:room-runtime:$version"
            const val ext = "androidx.room:room-ktx:$version"
            const val plugin = "androidx.room:room-compiler:$version"
        }
    }
}
