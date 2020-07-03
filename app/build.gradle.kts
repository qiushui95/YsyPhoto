import son.ysy.plugin.libs.AndroidXs
import son.ysy.plugin.libs.Kotlins
import son.ysy.plugin.libs.Singles
import son.ysy.plugin.libs.Tests
import son.ysy.plugin.libs.Views

plugins {
    id("son.ysy.plugin.libs")
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "son.ysy.ysyphoto"
        minSdkVersion(21)
        targetSdkVersion(29)
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
            keyAlias = "ysy"
            keyPassword = "ysy12345"
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
        pickFirst("META-INF/AL2.0")
        pickFirst("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    implementation(project(":lib_base"))

    implementation(Kotlins.stdlib)

    implementation(AndroidXs.coreKtx)

    implementation(AndroidXs.appcompat)

    implementation(Views.constraintLayout)

    testImplementation(Tests.jUnit)

    androidTestImplementation(Tests.Android.jUnit)

    androidTestImplementation(Tests.Android.espresso)

    implementation(Singles.component)
}