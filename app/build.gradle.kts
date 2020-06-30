plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}
android {
    compileSdkVersion(Libs.Build.target)
    buildToolsVersion(Libs.Build.tools)

    defaultConfig {
        applicationId = "son.ysy.photo"
        minSdkVersion(Libs.Build.min)
        targetSdkVersion(Libs.Build.target)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getAt("main").java.srcDirs("src/main/kotlin")
        getAt("debug").java.srcDirs("src/debug/kotlin")
        getAt("release").java.srcDirs("src/release/kotlin")
        getAt("test").java.srcDirs("src/test/kotlin")
        getAt("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    signingConfigs {
        create("dev") {
            keyAlias = KeyConfig.keyAlias
            keyPassword = KeyConfig.password
            storeFile = file("keys/ysy.jks")
            storePassword = keyPassword
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
//            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("dev")
        }
        getByName("release") {
            isMinifyEnabled = true
            isZipAlignEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("dev")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions{
        this.pickFirst("META-INF/library_release.kotlin_module")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    //kotlin
    implementation(Libs.Group.Kotlin.stdlib)
    implementation(Libs.Group.Kotlin.reflect)
    //协程
    implementation(Libs.Group.Coroutines.core)
    testImplementation(Libs.Group.Coroutines.test)
    //appcompat
    implementation(Libs.Single.appcompat)
    implementation(Libs.Single.coreKtx)
    //fragment
    implementation(Libs.Group.Fragment.core)
    implementation(Libs.Group.Fragment.ktx)
    //navigation
    implementation(Libs.Group.Navigation.fragmentKtx)
    implementation(Libs.Group.Navigation.uiKtx)
    //下拉刷新
    implementation(Libs.Group.View.refreshLayout)
    //约束布局
    implementation(Libs.Group.View.constraint)
    //util
    implementation(Libs.Single.util)
    //圆角图片
    implementation(Libs.Group.View.roundedImageView)
    //lifecycle
    implementation(Libs.Group.Lifecycle.runtime)
    implementation(Libs.Group.Lifecycle.liveDataKtx)
    implementation(Libs.Group.Lifecycle.viewModel)
    //图片加载
    implementation(Libs.Group.Glide.core)
    //epoxy
    implementation(Libs.Group.Epoxy.core)
    implementation(Libs.Group.View.recyclerview)
    kapt(Libs.Group.Epoxy.processor)
    //mmkv
    implementation(Libs.Single.mmkv)
    //init
    implementation(Libs.Single.init)
    //koin
    implementation(Libs.Group.Koin.scope)
    implementation(Libs.Group.Koin.viewModel)
    implementation(Libs.Group.Koin.ext)
    //json
    implementation(Libs.Group.MoShi.core)
    kapt(Libs.Group.MoShi.plugin)
    //retrofit
    implementation(Libs.Group.Retrofit.core)
    implementation(Libs.Group.Retrofit.moshi)
    debugImplementation(Libs.Group.Retrofit.interceptor)
    debugImplementation(Libs.Group.Retrofit.chucker)
    //okHttp
    implementation(Libs.Group.OkHttp.core)
    //drawable生成器
    implementation(Libs.Single.background)
    //lottie动画框架
    implementation(Libs.Group.View.lottie)
    //带加载的button
    implementation(Libs.Group.View.loadingButton)
    //内存卸载检测框架
    debugImplementation(Libs.Single.leakCanary)
    //stetho
    debugImplementation(Libs.Group.Stetho.core)
    debugImplementation(Libs.Group.Stetho.okHttp)
    //时间处理
    implementation(Libs.Single.joda)
    //room数据库
    implementation(Libs.Group.Room.core)
    implementation(Libs.Group.Room.ext)
    kapt(Libs.Group.Room.plugin)
    //Paris
    implementation(Libs.Group.Paris.core)
    kapt(Libs.Group.Paris.plugin)
    //崩溃展示
    debugImplementation(Libs.Single.spiderMan)
    //bottomNavigation
    implementation(Libs.Group.View.bottomNavigation)
    //viewPager2
    implementation(Libs.Group.View.viewPager2)
    //实时背景高斯模糊
    implementation(Libs.Group.View.realTimeBlurView)
    //弹窗框架
    implementation(Libs.Single.popup)
    //颜色帮助库
    implementation(Libs.Single.colorKtx)

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = (kotlinOptions.freeCompilerArgs + listOf(
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
    )).distinct()
}