import Libs.Dagger.addDagger
import Libs.Kotlin.addKotlin
import Libs.Room.addRoom
import org.jetbrains.dokka.DokkaDefaults.moduleName

plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "${BuildConfig.namespace}.services"
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
    addRoom()

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.localBroadcastManager)
    androidTestImplementation(Libs.AndroidX.testRunner)

    implementation(Libs.Google.gson)
    implementation(Libs.Google.materialDesign)

    testImplementation(Libs.jUnit)

    implementation(project(":core"))

    implementation(Libs.uCrop)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = BuildConfig.groupId
            artifactId = "services"
            version = BuildConfig.version

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
