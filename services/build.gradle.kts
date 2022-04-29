import Libs.Dagger.addDagger
import Libs.Kotlin.addKotlin
import Libs.Room.addRoom

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

group = BuildConfig.groupId
version = BuildConfig.versionCode

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

    publishing{
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

    testImplementation(Libs.jUnit)

    implementation(project(":core"))

    implementation(Libs.uCrop)
}

publishing {
    publications {
        create<MavenPublication>("mavenLocal") {
            groupId = BuildConfig.groupId
            artifactId = "services"
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
