import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

apply(plugin = "com.github.ben-manes.versions")

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.Android.gradle)
        classpath(Plugins.Kotlin.androidExtensions)
        classpath(Plugins.Kotlin.gradlePlugin)
        classpath(Plugins.Other.androidMavenPlugin)
        classpath(Plugins.Other.dependencyUpdates)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = Constants.JIT_PACK_URL)
    }

    apply(plugin = "com.github.ben-manes.versions")
}

tasks.register("clean", Delete::class) { delete(rootProject.buildDir) }

fun isNonStable(version: String): Boolean {
    return listOf("alpha", "beta", "rc", "cr", "m", "preview")
        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
        .any { nonStableIdentifier -> nonStableIdentifier.matches(version) }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
