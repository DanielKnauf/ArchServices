import Versions.vAndroidMaven
import Versions.vDependencyUpdates
import Versions.vGradle
import Versions.vKotlin

object Plugins {
    const val gradle = "com.android.tools.build:gradle:$vGradle"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$vKotlin"
    const val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:$vKotlin"
    const val dependencyUpdates = "com.github.ben-manes:gradle-versions-plugin:$vDependencyUpdates"
    const val androidMaven = "com.github.dcendents:android-maven-gradle-plugin:$vAndroidMaven"
}
