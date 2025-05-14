rootProject.name = "xmc-launcher"

plugins {
    // Use the Foojay Toolchains plugin to automatically download JDKs required by subprojects.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

dependencyResolutionManagement {

    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

listOf("api", "application", "common", "launcher-backend", "elevation-service").forEach { include(it) }
