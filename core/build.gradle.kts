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

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //androidX
    implementation(Dependencies.androidX_app_compat)
    implementation(Dependencies.AndroidX.localBroadcastManager)
    implementation(Dependencies.androidX_core_ktx)

    implementation(Dependencies.android_material_design)

    //google
    implementation(Dependencies.gson)

    //lifecycle
    implementation(Dependencies.AndroidX.viewModel)
    implementation(Dependencies.AndroidX.extensions)
    kapt(Dependencies.AndroidX.compiler)

    //testing
    androidTestImplementation(Dependencies.androidX_test_runner)
    testImplementation(Dependencies.jUnit)

    //dagger2
    implementation(Dependencies.Dagger.core)
    implementation(Dependencies.Dagger.android)
    implementation(Dependencies.Dagger.androidSupport)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.androidProcessor)

    //kotlin
    implementation(Dependencies.kotlin_reflect)
    implementation(Dependencies.kotlin_stdlib)
}
