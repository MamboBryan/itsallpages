import org.gradle.api.artifacts.dsl.DependencyHandler

const val kotlinVersion = "1.3.21"
const val hiltVersion = "2.39.1"
const val navVersion = "2.4.1"
const val roomVersion = "2.4.2"
const val glideVersion = "4.13.1"

object GradlePlugins {
    private object Versions {
        const val buildToolsVersion = "3.3.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val navGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
    const val googleServicesPlugin = "com.google.gms:google-services:4.3.10"

}

object BuildPlugins {

    const val androidLibrary = "com.android.library"
    const val androidApplication = "com.android.application"

    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"

    const val hilt = "dagger.hilt.android.plugin"
    const val navigation = "androidx.navigation.safeargs.kotlin"
    const val googleServices = "com.google.gms.google-services"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"

}

object AndroidSdk {
    const val appId = "com.mambobryan.samba"
    const val min = 22
    const val compile = 32
    const val target = 32
    const val versionCode = 1
    val versionName = "1.0.0-samba"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object GoogleLibraries {

    private object Versions {
        const val appcompat = "1.4.1"
        const val constraintLayout = "2.1.3"
        const val material = "1.5.0"
        const val lifecycle = "2.3.0"
        const val lifecycleExtensions = "2.2.0"
        const val coroutines = "1.5.2"
        const val playCoroutines = "1.3.7"
        const val paging = "3.1.1"
        const val pagingRoom = "2.4.2"
        const val pagingCompose = "1.0.0-alpha14"
        const val hiltWork = "1.0.0"
        const val workManager = "2.7.1"
        const val androidXStartup = "1.1.0"
        const val datastore = "1.0.0-alpha08"
        const val playServicesAuth = "19.0.0"
        const val support = "27.1.0"
        const val legacy = "1.0.0"
        const val splashScreen = "1.0.0-beta01"
    }

    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val legacySupport by lazy { "androidx.legacy:legacy-support-v4:${Versions.legacy}" }
    val playServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}" }
    val support by lazy { "com.android.support:design:${Versions.support}" }

    val hilt by lazy { "com.google.dagger:hilt-android:$hiltVersion" }
    val navigationCommon by lazy { "androidx.navigation:navigation-common-ktx:$navVersion" }
    val navigationFragment by lazy { ("androidx.navigation:navigation-fragment:$navVersion") }
    val navigationUi by lazy { ("androidx.navigation:navigation-ui:$navVersion") }
    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.datastore}" }
    val viewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" }
    val liveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" }
    val savedState by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}" }
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val lifecycleExtensions by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}" }
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val room by lazy { "androidx.room:room-ktx:$roomVersion" }
    val roomRuntime by lazy { "androidx.room:room-runtime:$roomVersion" }
    val pagingRoom by lazy { "androidx.room:room-paging:${Versions.pagingRoom}" }
    val paging by lazy { "androidx.paging:paging-runtime:${Versions.paging}" }
    val pagingCompose by lazy {"androidx.paging:paging-compose:${Versions.pagingCompose}"}
    val hiltWork by lazy { "androidx.hilt:hilt-work:${Versions.hiltWork}" }
    val hiltWorkCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltWork}" }
    val workManager by lazy { "androidx.work:work-runtime-ktx:${Versions.workManager}" }
    val androidXStartup by lazy { "androidx.startup:startup-runtime:${Versions.androidXStartup}" }
    val splashScreen by lazy { "androidx.core:core-splashscreen:${Versions.splashScreen}" }
    val playCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.playCoroutines}" }
}

object Libraries {

    private object Versions {
        const val appcompat = "1.4.1"
        const val constraintLayout = "2.1.3"
        const val ktx = "1.7.0"
        const val material = "1.5.0"
        const val retrofit = "2.9.0"
        const val timber = "5.0.1"
        const val gson = "2.9.0"
        const val alert = "1.0.1"
        const val alerter = "7.2.4"
        const val delegate = "1.0.0"

    }

    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"

    const val alert = "com.irozon.alertview:alertview:${Versions.alert}"
    const val alerter = "com.github.tapadoo:alerter:${Versions.alerter}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val glide = "com.github.bumptech.glide:glide$glideVersion"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val okHttp = "com.squareup.okhttp3:okhttp"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor"

    const val delegate = "com.github.Zhuinden:fragmentviewbindingdelegate-kt:${Versions.delegate}"

}

object Bom {

    private object Versions {
        const val firebase = "29.1.0"
        const val okhttp = "4.9.3"
    }

    const val firebase = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val okHttp = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"

}

object Firebase {
    val firestore by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val analytics by lazy { "com.google.firebase:firebase-analytics-ktx" }
}

object Kapt {
    val hilt by lazy { "com.google.dagger:hilt-android-compiler:$hiltVersion" }
    val glide by lazy { "com.github.bumptech.glide:compiler:$glideVersion" }
    val room by lazy { "androidx.room:room-compiler:$roomVersion" }
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13.2"
        const val testRunner = "1.1.3"
        const val espresso = "3.4.0"
    }

    val junit4 by lazy { "junit:junit:${Versions.junit4}" }
    val testRunner by lazy { "androidx.test:runner:${Versions.testRunner}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }

}

fun DependencyHandler.lifecycle() {
    implementation(GoogleLibraries.liveData)
    implementation(GoogleLibraries.lifecycle)
    implementation(GoogleLibraries.viewModel)
    implementation(GoogleLibraries.savedState)
    implementation(GoogleLibraries.lifecycleExtensions)
}

fun DependencyHandler.navigation() {
    implementation(GoogleLibraries.navigationUi)
    implementation(GoogleLibraries.navigationCommon)
    implementation(GoogleLibraries.navigationFragment)
}

fun DependencyHandler.hilt() {
    implementation(GoogleLibraries.hilt)
    kapt(Kapt.hilt)
}

fun DependencyHandler.room(){
    implementation(GoogleLibraries.roomRuntime)
    implementation(GoogleLibraries.room)
    kapt(Kapt.room)
}

fun DependencyHandler.firebase() {
    implementation(platform(Bom.firebase))
    implementation(Firebase.analytics)
    implementation(Firebase.firestore)
    implementation(GoogleLibraries.playCoroutines)
}

fun DependencyHandler.retrofit() {
    implementation(platform(Bom.okHttp))
    implementation(Libraries.okHttp)
    implementation(Libraries.okHttpLogging)

    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitConverter)
}

fun DependencyHandler.paging(){
    implementation(GoogleLibraries.paging)
    implementation(GoogleLibraries.pagingRoom)
    implementation(GoogleLibraries.pagingCompose)
}