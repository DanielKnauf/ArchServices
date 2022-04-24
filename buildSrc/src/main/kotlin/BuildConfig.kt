import org.gradle.api.JavaVersion

object BuildConfig {
    const val minSdk = 24
    const val targetSdk = 31
    const val compileSdkVersion = 31
    const val versionCode = 9
    const val versionName = "0.8.0"

    val javaVersion by lazy { JavaVersion.VERSION_1_8 }
}
