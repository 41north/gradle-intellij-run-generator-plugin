package dev.north.fortyone.gradle.intellij.run.generator.tasks

import dev.north.fortyone.gradle.intellij.run.generator.YAML
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.security.InvalidParameterException

/**
 * Task that allows to generate XML Run configurations for IntelliJ.
 */
open class IntellijRunConfiguratorTask : DefaultTask() {

  @InputFile
  lateinit var tasksDefinitionsFile: File

  @OutputDirectory
  lateinit var taskDefinitionsOutput: File

  init {
    group = "Intellij"
    description = "Generates XML Run configurations files for Intellij"
  }

  @TaskAction
  fun run() {
    // Ensure output dir exists and create it if necessary
    val runConfigurationDir = taskDefinitionsOutput.apply { if (!exists()) mkdirs() }

    // Ensure definition file exists
    if (!tasksDefinitionsFile.exists())
      throw InvalidParameterException("Run configuration file ${tasksDefinitionsFile.absolutePath} not found!")

    val definitions = YAML().loadAll(tasksDefinitionsFile.inputStream()).iterator()
//    definitions
//      .forEach { config ->
//        File(runConfigurationDir, config.filename.replace("\\s", "_")).writeText(config.toXml())
//      }
  }
}
