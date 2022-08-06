import Libs.Dagger.addDagger
import Libs.Kotlin.addKotlin
import Libs.Lifecycle.addLifecycle
import org.jetbrains.dokka.DokkaDefaults.moduleName

plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "${BuildConfig.namespace}.core"
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk

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

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
    }

    kotlinOptions {
        jvmTarget = BuildConfig.jvmVersion
    }

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
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

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = BuildConfig.groupId
            artifactId = "core"
            version = BuildConfig.version

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
