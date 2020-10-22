import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

apply(plugin = "com.github.ben-manes.versions")

group = Constants.group_name

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.gradle)
        classpath(Dependencies.kotlin_gradle_plugin)
        classpath(Dependencies.kotlin_android_extensions)
        classpath(Dependencies.dependency_updates)
        classpath(Dependencies.android_maven_plugin)
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

subprojects{
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

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    // disallow release candidates as upgradable versions from stable versions
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
