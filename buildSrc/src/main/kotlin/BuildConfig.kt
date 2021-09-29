import org.gradle.api.JavaVersion

object BuildConfig {
    const val minSdk = 21
    const val targetSdk = 30
    const val compileSdkVersion = 30
    const val versionCode = 6
    const val versionName = "0.5.0"

    val javaVersion by lazy { JavaVersion.VERSION_1_8 }
}
