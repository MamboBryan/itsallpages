pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}
rootProject.name = "Samba"

// MAIN APP MODULE
include (":app")

// COMMON MODULE
include(":common")

// UTIL MODULES
include(":modules:ui")
include(":modules:navigation")

// DATA MODULES
include(":data:model")
include(":data:local")
include(":data:remote")
