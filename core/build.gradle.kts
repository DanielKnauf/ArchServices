import Libs.Dagger.addDagger
import Libs.Kotlin.addKotlin
import Libs.Lifecycle.addLifecycle

plugins {
    id("com.android.library")
    // id("com.github.dcendents.android-maven")
    kotlin("android")
    kotlin("kapt")
}

group = Constants.GROUP_NAME

android {
    compileSdkVersion(BuildConfig.compileSdkVersion)

    defaultConfig {
        minSdkVersion(BuildConfig.minSdk)
        targetSdkVersion(BuildConfig.targetSdk)
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    compileOptions {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
    }

    kotlinOptions {
        jvmTarget = BuildConfig.javaVersion.toString()
    }

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    addDagger()
    addKotlin()
    addLifecycle()

    //androidX
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.localBroadcastManager)
    androidTestImplementation(Libs.AndroidX.testRunner)

    implementation(Libs.Google.gson)
    implementation(Libs.Google.materialDesign)

    testImplementation(Libs.jUnit)
}
