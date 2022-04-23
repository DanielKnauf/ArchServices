import Versions.AndroidX.vAppCompat
import Versions.AndroidX.vBroadcastManager
import Versions.AndroidX.vBrowser
import Versions.AndroidX.vConstraintLayout
import Versions.AndroidX.vCore
import Versions.AndroidX.vFragment
import Versions.AndroidX.vLegacySupport
import Versions.AndroidX.vLifecycle
import Versions.AndroidX.vLifecycleExtensions
import Versions.AndroidX.vNavigation
import Versions.AndroidX.vPaging
import Versions.AndroidX.vRecyclerView
import Versions.AndroidX.vRoom
import Versions.AndroidX.vTestRunner
import Versions.AndroidX.vTransition
import Versions.AndroidX.vViewPager2
import Versions.DK.vLiveDataKit
import Versions.Google.vDagger
import Versions.Google.vFirebaseAnalytics
import Versions.Google.vFirebaseCrashlytics
import Versions.Google.vGson
import Versions.Google.vHilt
import Versions.Google.vMaterial
import Versions.Kotlin.vCoroutines
import Versions.Kotlin.vKotlin
import Versions.Other.vJUnit
import Versions.Other.vKtLint
import Versions.Other.vPicasso
import Versions.Other.vRetrofit
import Versions.Other.vUCrop
import org.gradle.kotlin.dsl.DependencyHandlerScope

object Libs {

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:$vAppCompat"
        const val browser = "androidx.browser:browser:$vBrowser"
        const val core = "androidx.core:core-ktx:$vCore"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$vConstraintLayout"
        const val fragment = "androidx.fragment:fragment-ktx:$vFragment"
        const val legacySupport = "androidx.legacy:legacy-support-v4:$vLegacySupport"
        const val localBroadcastManager =
            "androidx.localbroadcastmanager:localbroadcastmanager:$vBroadcastManager"
        const val paging = "androidx.paging:paging-runtime:$vPaging"
        const val recyclerView = "androidx.recyclerview:recyclerview:$vRecyclerView"
        const val testRunner = "androidx.test:runner:$vTestRunner"
        const val transition = "androidx.transition:transition-ktx:$vTransition"
        const val viewPager2 = "androidx.viewpager2:viewpager2:$vViewPager2"
    }

    object Coroutines {
        private const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$vCoroutines"
        private const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$vCoroutines"

        fun DependencyHandlerScope.addCoroutines() {
            "implementation"(core)
            "implementation"(android)
        }
    }

    object Dagger {
        private const val core = "com.google.dagger:dagger:$vDagger"
        private const val android = "com.google.dagger:dagger-android:$vDagger"
        private const val androidSupport = "com.google.dagger:dagger-android-support:$vDagger"
        private const val compiler = "com.google.dagger:dagger-compiler:$vDagger"
        private const val androidProcessor = "com.google.dagger:dagger-android-processor:$vDagger"

        fun DependencyHandlerScope.addDagger() {
            "implementation"(core)
            "implementation"(android)
            "implementation"(androidSupport)
            "kapt"(compiler)
            "kapt"(androidProcessor)
        }
    }

    object DK {
        const val liveDataKit = "com.github.DanielKnauf:livedata-kit:$vLiveDataKit"
    }

    object Firebase {
        private const val crashlytics =
            "com.google.firebase:firebase-crashlytics-ktx:$vFirebaseCrashlytics"
        private const val analytics =
            "com.google.firebase:firebase-analytics-ktx:$vFirebaseAnalytics"

        fun DependencyHandlerScope.addFirebase() {
            "implementation"(crashlytics)
            "implementation"(analytics)
        }
    }

    object Google {
        const val materialDesign = "com.google.android.material:material:$vMaterial"
        const val gson = "com.google.code.gson:gson:$vGson"
    }

    object Hilt {
        private const val android = "com.google.dagger:hilt-android:$vHilt"
        private const val compiler = "com.google.dagger:hilt-compiler:$vHilt"

        fun DependencyHandlerScope.addHilt() {
            "implementation"(android)
            "kapt"(compiler)
        }
    }

    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$vKotlin"
        private const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$vKotlin"

        fun DependencyHandlerScope.addKotlin() {
            "implementation"(reflect)
            "implementation"(stdlib)
        }
    }

    object Lifecycle {
        private const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:$vLifecycle"
        private const val lifecycleExt =
            "androidx.lifecycle:lifecycle-extensions:$vLifecycleExtensions"
        private const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$vLifecycle"

        fun DependencyHandlerScope.addLifecycle() {
            "implementation"(lifecycle)
            "implementation"(lifecycleExt)
            "kapt"(lifecycleCompiler)
        }
    }

    object Navigation {
        private const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:$vNavigation"
        private const val navigationUi = "androidx.navigation:navigation-ui-ktx:$vNavigation"

        fun DependencyHandlerScope.addNavigation() {
            "implementation"(navigationFragment)
            "implementation"(navigationUi)
        }
    }

    object Retrofit {
        private const val core = "com.squareup.retrofit2:retrofit:$vRetrofit"
        private const val gson = "com.squareup.retrofit2:converter-gson:$vRetrofit"

        fun DependencyHandlerScope.addRetrofit() {
            "implementation"(core)
            "implementation"(gson)
        }
    }

    object Room {
        private const val runtime = "androidx.room:room-runtime:$vRoom"
        private const val ktx = "androidx.room:room-ktx:$vRoom"
        private const val compiler = "androidx.room:room-compiler:$vRoom"

        fun DependencyHandlerScope.addRoom() {
            "implementation"(runtime)
            "implementation"(ktx)
            "kapt"(compiler)
        }
    }

    const val ktLint = "com.pinterest:ktlint:$vKtLint"
    const val jUnit = "junit:junit:$vJUnit"
    const val picasso = "com.squareup.picasso:picasso:$vPicasso"
    const val uCrop = "com.github.yalantis:ucrop:$vUCrop"
}
