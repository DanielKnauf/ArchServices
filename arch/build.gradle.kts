plugins {
    id("com.android.library")
    id("com.github.dcendents.android-maven")
    kotlin("android")
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //androidX
    implementation(Dependencies.androidX_app_compat)
    implementation(Dependencies.AndroidX.recyclerView)
    implementation(Dependencies.androidX_core_ktx)
    implementation(Dependencies.android_material_design)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.viewPager2)

    //lifecycle
    implementation(Dependencies.AndroidX.viewModel)
    implementation(Dependencies.AndroidX.extensions)
    kapt(Dependencies.AndroidX.compiler)

    //picasso
    implementation(Dependencies.picasso)

    //dagger2
    implementation(Dependencies.Dagger.core)
    implementation(Dependencies.Dagger.android)
    implementation(Dependencies.Dagger.androidSupport)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.androidProcessor)

    //testing
    androidTestImplementation(Dependencies.androidX_test_runner)
    testImplementation(Dependencies.jUnit)

    //retrofit
    implementation(Dependencies.retrofit_core)
    implementation(Dependencies.retrofit_gson)

    //kotlin
    implementation(Dependencies.kotlin_reflect)
    implementation(Dependencies.kotlin_stdlib)

    implementation(project(":core"))
}
