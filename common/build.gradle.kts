plugins {
    id("com.gradleup.shadow")
}

javafx {
    modules = listOf("javafx.controls")
    version = libs.versions.javafx.get()
}

repositories {
    mavenCentral()
}

dependencies {
    api("com.google.code.gson:gson:${libs.versions.gson.get()}")
    api("net.java.dev.jna:jna:${libs.versions.jna.get()}")
    api("net.java.dev.jna:jna-platform:${libs.versions.jna.get()}")
}

project.tasks.build {
    dependsOn(tasks.shadowJar)
}