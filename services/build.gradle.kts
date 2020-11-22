plugins {
    id("com.android.library")
    id("com.github.dcendents.android-maven")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

group = Constants.group_name

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(ReleaseConfig.minSdk)
        targetSdkVersion(ReleaseConfig.targetSdk)
        versionCode = ReleaseConfig.versionCode
        versionName = ReleaseConfig.versionName

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

    "implementation"(project(":core"))
}
