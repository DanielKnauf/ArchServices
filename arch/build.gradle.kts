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

    buildFeatures {
        dataBinding = true
    }

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
    "implementation"(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //androidX
    "implementation"(Dependencies.androidX_app_compat)
    "implementation"(Dependencies.androidX_recyclerView)
    "implementation"(Dependencies.androidX_core_ktx)
    "implementation"(Dependencies.android_material_design)
    "implementation"(Dependencies.androidX_legacy_support)
    "implementation"(Dependencies.androidX_viewPager2)

    //lifecycle
    "implementation"(Dependencies.androidX_lifecycle_viewModel)
    "implementation"(Dependencies.androidX_lifecycle_extensions)
    "kapt"(Dependencies.androidX_lifecycle_compiler)

    //picasso
    "implementation"(Dependencies.picasso)

    //dagger2
    "implementation"(Dependencies.dagger_core)
    "implementation"(Dependencies.dagger_android)
    "implementation"(Dependencies.dagger_android_support)
    "kapt"(Dependencies.dagger_compiler)
    "kapt"(Dependencies.dagger_android_processor)

    //testing
    "androidTestImplementation"(Dependencies.androidX_test_runner)
    "testImplementation"(Dependencies.jUnit)

    //retrofit
    "implementation"(Dependencies.retrofit_core)
    "implementation"(Dependencies.retrofit_gson)

    //kotlin
    "implementation"(Dependencies.kotlin_reflect)
    "implementation"(Dependencies.kotlin_stdlib)

    "implementation"(project(":core"))
}
