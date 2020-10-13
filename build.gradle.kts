import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

apply(plugin = "com.github.ben-manes.versions")

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
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    apply(plugin = "com.github.ben-manes.versions")
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
