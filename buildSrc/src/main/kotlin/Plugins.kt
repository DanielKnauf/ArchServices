import Versions.Android.vGradle
import Versions.AndroidX.vNavigation
import Versions.Google.vFirebaseCrashlyticsPlugin
import Versions.Google.vHilt
import Versions.Google.vServices
import Versions.Kotlin.vKotlin
import Versions.Other.vAndroidMaven
import Versions.Other.vDependencyUpdates

object Plugins {

    object Android {
        const val gradle = "com.android.tools.build:gradle:$vGradle"
    }

    object AndroidX {
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$vNavigation"
    }

    object Google {
        const val firebaseCrashlytics =
            "com.google.firebase:firebase-crashlytics-gradle:$vFirebaseCrashlyticsPlugin"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:$vHilt"
        const val services = "com.google.gms:google-services:$vServices"
    }

    object Kotlin {
        const val androidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:$vKotlin"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$vKotlin"
    }

    object Other {
        const val androidMavenPlugin =
            "com.github.dcendents:android-maven-gradle-plugin:$vAndroidMaven"
        const val dependencyUpdates =
            "com.github.ben-manes:gradle-versions-plugin:$vDependencyUpdates"
    }
}
