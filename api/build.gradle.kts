import buildsrc.convention.org.ttlzmc.xmc.Config

plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("com.gradleup.shadow")
}

javafx {
    modules = listOf("javafx.controls")
    version = libs.versions.javafx.get()
}

dependencies {
    implementation(project(Config.Projects.COMMON))
}
