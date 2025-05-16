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
    version = libs.versions.xmc.get()
    description = "Creates a launcher bundle for Windows"

    from(project.sourceSets.main.get().output) {
        exclude("**/linux/**")
    }

    from(project(":elevation-service").sourceSets.main.get().output) {
        include("**")
    }

    configurations = listOf(project.configurations.runtimeClasspath.get())
    dependsOn(project(":launcher-backend").tasks.named("jar"))

    archiveBaseName.set("xmc-launcher-windows")
    archiveVersion.set(libs.versions.xmc)
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs/out/windows"))

    manifest {
        attributes["Main-Class"] = "org.ttlzmc.xmc.BootstrapKt"
    }
}

tasks.register<ShadowJar>("compileLinux") {
    group = "build"
    version = libs.versions.xmc.get()
    description = "Creates a launcher bundle for Linux"

    from(project.sourceSets.main.get().output) {
        exclude("**/windows/**")
    }

    from(project(":elevation-service").sourceSets.main.get().output) {
        include("**")
    }

    configurations = listOf(project.configurations.runtimeClasspath.get())

    archiveBaseName.set("xmc-launcher-linux")
    archiveVersion.set(libs.versions.xmc)
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs/out/linux"))

    manifest {
        attributes["Main-Class"] = "org.ttlzmc.xmc.BootstrapKt"
    }
}

tasks.named("build") {
    dependsOn("compileWindows", "compileLinux")
}

project.tasks.build {
    dependsOn(tasks.shadowJar)
}