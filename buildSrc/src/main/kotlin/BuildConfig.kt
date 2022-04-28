import org.gradle.api.JavaVersion

object BuildConfig {
    const val minSdk = 24
    const val targetSdk = 31
    const val compileSdkVersion = 31

    const val groupId = "knaufdan.android"
    const val versionCode = "0.9.0"

    val javaVersion = JavaVersion.VERSION_11
    const val jvmVersion = "11"
}
