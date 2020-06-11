// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        RepositoryUtil.config(this)
    }

    dependencies {
        classpath(Libs.Build.plugin)
        classpath(Libs.Group.Kotlin.plugin)
        classpath(Libs.Group.Navigation.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        RepositoryUtil.config(this)
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}