import Versions.androidx_core_version
import Versions.appcompat_version
import Versions.vBroadcastManager
import Versions.vDagger
import Versions.design_version
import Versions.gson_version
import Versions.vKotlin
import Versions.ktLint_version
import Versions.vLegacySupport
import Versions.picasso_version
import Versions.vRecyclerView
import Versions.retrofit_version
import Versions.room_version
import Versions.test_runner_version
import Versions.vAndroidXLifecycleExtensions
import Versions.vAndroidXLifecycle
import Versions.vJUnit
import Versions.vViewPager2

object Dependencies {
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:$vKotlin"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$vKotlin"

    val android_material_design = "com.google.android.material:material:$design_version"

    val androidX_core_ktx = "androidx.core:core-ktx:$androidx_core_version"
    val androidX_app_compat = "androidx.appcompat:appcompat:$appcompat_version"

    object AndroidX {
        val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$vAndroidXLifecycle"
        val extensions = "androidx.lifecycle:lifecycle-extensions:$vAndroidXLifecycleExtensions"
        val compiler = "androidx.lifecycle:lifecycle-compiler:$vAndroidXLifecycle"
        val recyclerView = "androidx.recyclerview:recyclerview:$vRecyclerView"
        val viewPager2 = "androidx.viewpager2:viewpager2:$vViewPager2"
        val legacy_support = "androidx.legacy:legacy-support-v4:$vLegacySupport"
        val localbroadcastManager = "androidx.localbroadcastmanager:localbroadcastmanager:$vBroadcastManager"
    }

    val jUnit = "junit:junit:$vJUnit"
    val androidX_test_runner = "androidx.test:runner:$test_runner_version"

    object Dagger {
        val core = "com.google.dagger:dagger:$vDagger"
        val android = "com.google.dagger:dagger-android:$vDagger"
        val androidSupport = "com.google.dagger:dagger-android-support:$vDagger"
        val compiler = "com.google.dagger:dagger-compiler:$vDagger"
        val androidProcessor = "com.google.dagger:dagger-android-processor:$vDagger"
    }

    val gson = "com.google.code.gson:gson:$gson_version"

    val picasso = "com.squareup.picasso:picasso:$picasso_version"

    //room
    const val androidX_room = "androidx.room:room-runtime:$room_version"
    const val androidX_room_ktx = "androidx.room:room-ktx:$room_version"
    const val androidX_room_compiler = "androidx.room:room-compiler:$room_version"

    //retrofit
    val retrofit_core = "com.squareup.retrofit2:retrofit:$retrofit_version"
    val retrofit_gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"

    //maintenance
    val ktLint = "com.pinterest:ktlint:$ktLint_version"
}
