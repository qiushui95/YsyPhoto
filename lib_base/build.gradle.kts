import son.ysy.plugin.libs.AndroidXs
import son.ysy.plugin.libs.Kotlins
import son.ysy.plugin.libs.Jsons
import son.ysy.plugin.libs.Coroutines
import son.ysy.plugin.libs.Singles
import son.ysy.plugin.libs.Lifecycles
import son.ysy.plugin.libs.Epoxys
import son.ysy.plugin.libs.Views
import son.ysy.plugin.libs.Paris

plugins {
    id("son.ysy.plugin.libs")
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    sourceSets {
        getAt("main").java.srcDirs("src/main/kotlin")
        getAt("debug").java.srcDirs("src/debug/kotlin")
        getAt("release").java.srcDirs("src/release/kotlin")
        getAt("test").java.srcDirs("src/test/kotlin")
        getAt("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    api(Kotlins.stdlib)

    api(AndroidXs.coreKtx)

    api(AndroidXs.appcompat)

    api(Jsons.core)
    kapt(Jsons.compiler)

    api(Coroutines.core)
    debugApi(Coroutines.test)

    api(Singles.util)

    api(Lifecycles.common)
    api(Lifecycles.runtime)
    api(Lifecycles.liveData)
    api(Lifecycles.viewModel)
    api(Lifecycles.savedState)

    api(Epoxys.core)
    kapt(Epoxys.compiler)

    api(Views.constraintLayout)

    api(Paris.core)
    kapt(Paris.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = (kotlinOptions.freeCompilerArgs + listOf(
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
    )).distinct()
}