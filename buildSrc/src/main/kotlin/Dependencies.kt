import Versions.android_maven_version
import Versions.androidx_core_version
import Versions.appcompat_version
import Versions.broadcast_manager_version
import Versions.dagger_version
import Versions.dependency_updates_version
import Versions.gradle_version
import Versions.gson_version
import Versions.jUnit_version
import Versions.kotlin_version
import Versions.ktLint_version
import Versions.lifecycle_version
import Versions.picasso_version
import Versions.recyclerView_version
import Versions.retrofit_version
import Versions.test_runner_version

object Dependencies {
    val gradle = "com.android.tools.build:gradle:$gradle_version"

    val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    val kotlin_android_extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$kotlin_version"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    val androidX_core_ktx = "androidx.core:core-ktx:$androidx_core_version"
    val androidX_app_compat = "androidx.appcompat:appcompat:$appcompat_version"
    val androidX_localbroadcast_manager =
        "androidx.localbroadcastmanager:localbroadcastmanager:$broadcast_manager_version"
    val androidX_recyclerView = "androidx.recyclerview:recyclerview:$recyclerView_version"

    val androidX_lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
    val androidX_lifecycle_viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    val androidX_lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    val jUnit = "junit:junit:$jUnit_version"
    val androidX_test_runner = "androidx.test:runner:$test_runner_version"

    val dagger_core = "com.google.dagger:dagger:$dagger_version"
    val dagger_android = "com.google.dagger:dagger-android:$dagger_version"
    val dagger_android_support = "com.google.dagger:dagger-android-support:$dagger_version"
    val dagger_compiler = "com.google.dagger:dagger-compiler:$dagger_version"
    val dagger_android_processor = "com.google.dagger:dagger-android-processor:$dagger_version"

    val gson = "com.google.code.gson:gson:$gson_version"

    val picasso = "com.squareup.picasso:picasso:$picasso_version"

    //retrofit
    val retrofit_core = "com.squareup.retrofit2:retrofit:$retrofit_version"
    val retrofit_gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"

    //maintenance
    val dependency_updates =
        "com.github.ben-manes:gradle-versions-plugin:$dependency_updates_version"
    val ktLint = "com.pinterest:ktlint:$ktLint_version"

    //jitpack
    val android_maven_plugin =
        "com.github.dcendents:android-maven-gradle-plugin:$android_maven_version"
}
