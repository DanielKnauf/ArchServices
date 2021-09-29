import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

apply(plugin = "com.github.ben-manes.versions")

group = Constants.GROUP_NAME

buildscript {
    repositories {
        google()
        jcenter()
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
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    apply(plugin = "maven-publish")
    apply(plugin = "com.github.ben-manes.versions")
}

subprojects {
    apply { plugin("maven") }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

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
