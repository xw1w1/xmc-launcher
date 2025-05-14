plugins {
    application
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version "0.1.0" apply false
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.openjfx.javafxplugin")
    apply(plugin = "org.jetbrains.kotlin.jvm")
}