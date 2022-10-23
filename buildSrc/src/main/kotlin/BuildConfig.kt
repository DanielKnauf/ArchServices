import org.gradle.api.JavaVersion

object BuildConfig {
    const val minSdk = 24
    const val targetSdk = 33
    const val compileSdkVersion = 33

    const val namespace = "knaufdan.android"
    const val groupId = "com.github.DanielKnauf"
    const val version = "0.13.0"

    val javaVersion = JavaVersion.VERSION_11
    const val jvmVersion = "11"
}
