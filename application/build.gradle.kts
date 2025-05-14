import buildsrc.convention.org.ttlzmc.xmc.Config
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
}

javafx {
    modules = listOf("javafx.controls")
    version = libs.versions.javafx.get()
}

dependencies {
    implementation(project(Config.Projects.COMMON))
    implementation(project(Config.Projects.BACKEND))
}

tasks.register<ShadowJar>("compileWindows") {
    group = "build"
    description = "Creates a launcher bundle for Windows"

    from(project.sourceSets.main.get().output) {
        exclude("**/linux/**")
    }

    configurations = listOf(project.configurations.runtimeClasspath.get())
    dependsOn(project(":launcher-backend").tasks.named("jar"))

    archiveBaseName.set("xmc-launcher-windows")
    archiveVersion.set(project.version.toString())
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs/windows"))
}

tasks.register<ShadowJar>("compileLinux") {
    group = "build"
    description = "Creates a launcher bundle for Linux"

    from(project.sourceSets.main.get().output) {
        exclude("**/windows/**")
    }

    configurations = listOf(project.configurations.runtimeClasspath.get())

    archiveBaseName.set("xmc-launcher-linux")
    archiveVersion.set(project.version.toString())
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs/linux"))
}

tasks.named("build") {
    dependsOn("compileWindows", "compileLinux")
}

project.tasks.build {
    dependsOn(tasks.shadowJar)
}