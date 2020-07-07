import son.ysy.plugin.build.BuildConfig
import son.ysy.plugin.libs.Components

plugins {
    id("son.ysy.plugin.libs")
    id("son.ysy.plugin.build")
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(BuildConfig.compileSdkVersion)
    buildToolsVersion(BuildConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(BuildConfig.minSdkVersion)
        targetSdkVersion(BuildConfig.targetSdkVersion)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                // 配置业务模块的模块名称
                arguments = mapOf("HOST" to "home")
            }
        }
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

    api(project(":lib_common"))

    kapt(Components.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs = (kotlinOptions.freeCompilerArgs + listOf(
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
    )).distinct()
}