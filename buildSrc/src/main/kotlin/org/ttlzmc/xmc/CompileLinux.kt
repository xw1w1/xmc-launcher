package buildsrc.convention.org.ttlzmc.xmc

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

abstract class CompileLinux : DefaultTask() {

    @get:Input
    abstract val outputDirectory: DirectoryProperty

    @get:InputFiles
    abstract val files: ConfigurableFileCollection

    @TaskAction
    fun execute() {

    }
}