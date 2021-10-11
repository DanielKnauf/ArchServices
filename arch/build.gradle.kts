import Libs.Dagger.addDagger
import Libs.Kotlin.addKotlin
import Libs.Lifecycle.addLifecycle
import Libs.Navigation.addNavigation
import Libs.Retrofit.addRetrofit

plugins {
    id("com.android.library")
    id("com.github.dcendents.android-maven")
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

    buildFeatures {
        dataBinding = true
    }

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    addDagger()
    addKotlin()
    addLifecycle()
    addNavigation()
    addRetrofit()

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.fragment)
    implementation(Libs.AndroidX.legacySupport)
    implementation(Libs.AndroidX.paging)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.AndroidX.transition)
    implementation(Libs.AndroidX.viewPager2)
    androidTestImplementation(Libs.AndroidX.testRunner)

    implementation(Libs.Google.materialDesign)

    implementation(Libs.picasso)

    testImplementation(Libs.jUnit)

    implementation(Libs.DK.liveDataKit)

    implementation(project(":core"))
}
