import Libs.Dagger.addDagger
import Libs.Kotlin.addKotlin
import Libs.Lifecycle.addLifecycle
import Libs.Navigation.addNavigation
import Libs.Retrofit.addRetrofit

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

group = Constants.GROUP_NAME

android {
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

    java {
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
    }

    kotlinOptions {
        jvmTarget = BuildConfig.jvmVersion
    }

    buildFeatures {
        dataBinding = true
    }

    apply(from = "$rootDir/buildSrc/ktlint.gradle.kts")
}

dependencies {
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = BuildConfig.groupId
            artifactId = "arch"
            version = BuildConfig.versionCode

            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")

            pom {
                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    configurations.getByName("implementation") {
                        dependencies.forEach {
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", it.group)
                            dependencyNode.appendNode("artifactId", it.name)
                            dependencyNode.appendNode("version", it.version)
                        }
                    }
                }
            }
        }
    }
}
