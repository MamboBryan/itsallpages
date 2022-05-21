import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val apiKey = gradleLocalProperties(rootDir).getProperty("API_KEY")

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hilt)
    id(BuildPlugins.googleServices)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        applicationId = AndroidSdk.appId
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName

        testInstrumentationRunner = AndroidSdk.testInstrumentationRunner

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

}

dependencies {

    implementation(project(":modules:ui"))

    implementation(Libraries.ktxCore)

    implementation(GoogleLibraries.appCompat)
    implementation(GoogleLibraries.material)
    implementation(GoogleLibraries.constraintLayout)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation(TestLibraries.espresso)

    implementation(GoogleLibraries.splashScreen)

    lifecycle()

    hilt()

    navigation()

    paging()

    room()

    firebase()

    retrofit()

    implementation(Libraries.delegate)
    implementation(Libraries.timber)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("io.coil-kt:coil:2.1.0")
    implementation("io.coil-kt:coil-compose:2.1.0")

    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("org.ocpsoft.prettytime:prettytime:4.0.4.Final")
    implementation("com.mikhaellopez:circularimageview:4.3.0")

    // Integration with activities
    implementation("androidx.activity:activity-compose:1.4.0")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.1.1")
    // Animations
    implementation("androidx.compose.animation:animation:1.1.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    // When using a MDC theme
    implementation ("com.google.android.material:compose-theme-adapter:1.1.9")

}