import buildsrc.convention.org.ttlzmc.xmc.Config

plugins {
    id("com.gradleup.shadow")
}

version = project.property("xmc.version").toString()

javafx {
    modules = listOf("javafx.controls")
    version = libs.versions.javafx.get()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Config.Projects.APPLICATION))
    implementation(project(Config.Projects.BACKEND))
    implementation(project(Config.Projects.COMMON))
    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    shadowJar {
        mergeServiceFiles()
        manifest {
            attributes["Main-Class"] = "org.ttlzmc.xmc.BootstrapKt"
        }
        archiveBaseName = "xmc-bootstrap"
        archiveClassifier = "all"
        archiveVersion = version.toString()
        destinationDirectory = file("${rootProject.rootDir}/build/libs")
    }
}

project.tasks.build {
    dependsOn(tasks.shadowJar)
}