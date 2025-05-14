import buildsrc.convention.org.ttlzmc.xmc.Config

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
    implementation(project(Config.Projects.COMMON))
    testImplementation("org.junit.jupiter:junit-jupiter-api:${libs.versions.jupiter.get()}")
}

tasks {
    test {
        useJUnitPlatform()
    }

    shadowJar {
        mergeServiceFiles()
    }
}

project.tasks.build {
    dependsOn(tasks.shadowJar)
}