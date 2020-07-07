// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.3.72")
    apply(from = file("mavenConfig.gradle.kts"))
    repositories {

        google()
        jcenter()
        maven(url = extra.get("mavenUrl").toString()) {
            credentials {
                username = extra.get("mavenUserName").toString()
                password = extra.get("mavenPassword").toString()
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("son.ysy.plugin:libs:1.0.2")
        classpath("son.ysy.plugin:build:1.0.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}