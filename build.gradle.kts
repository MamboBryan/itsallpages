// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath (GradlePlugins.hiltGradlePlugin)
        classpath (GradlePlugins.navGradlePlugin)
        classpath (GradlePlugins.googleServicesPlugin)
    }
}

plugins {
    id("com.android.application") version "7.1.1" apply false
    id("com.android.library") version "7.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}