import org.gradle.api.JavaVersion

/**
 * Current version: 0.8.0
 */
object BuildConfig {
    const val minSdk = 24
    const val targetSdk = 31
    const val compileSdkVersion = 31

    val javaVersion = JavaVersion.VERSION_11
    const val jvmVersion = "11"
}
