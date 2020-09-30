plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
    "implementation"(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //androidX
    "implementation"(Dependencies.androidX_app_compat)
    "implementation"(Dependencies.androidX_localbroadcast_manager)
    "implementation"(Dependencies.androidX_core_ktx)

    //google
    "implementation"(Dependencies.gson)

    //lifecycle
    "implementation"(Dependencies.androidX_lifecycle_viewModel)
    "implementation"(Dependencies.androidX_lifecycle_extensions)
    "kapt"(Dependencies.androidX_lifecycle_compiler)

    //testing
    "androidTestImplementation"(Dependencies.androidX_test_runner)
    "testImplementation"(Dependencies.jUnit)

    //dagger2
    "implementation"(Dependencies.dagger_core)
    "implementation"(Dependencies.dagger_android)
    "implementation"(Dependencies.dagger_android_support)
    "kapt"(Dependencies.dagger_compiler)
    "kapt"(Dependencies.dagger_android_processor)

    //kotlin
    "implementation"(Dependencies.kotlin_reflect)
    "implementation"(Dependencies.kotlin_stdlib)
}
