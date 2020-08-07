package dev.north.fortyone.gradle.intellij.run.generator.tasks

import dev.north.fortyone.gradle.intellij.run.generator.YAML
import dev.north.fortyone.gradle.intellij.run.generator.models.IntellijRunConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FilenameFilter
import java.security.InvalidParameterException

/**
 * Task that allows to generate XML Run configurations for IntelliJ.
 */
open class IntellijRunConfiguratorTask : DefaultTask() {

  lateinit var tasksDefinitions: File

  lateinit var tasksDefinitionsFileExtension: FilenameFilter

  @OutputDirectory
  lateinit var taskDefinitionsOutput: File

  init {
    group = "Intellij"
    description = "Generates XML Run configurations files for Intellij"
  }

  @TaskAction
  fun run() {

    // Ensure output dir exists (or create it if necessary)
    taskDefinitionsOutput.apply { if (!exists()) mkdirs() }

    // If doesn't exist, throw error
    if (!tasksDefinitions.exists())
      throw InvalidParameterException("File or path ${tasksDefinitions.absolutePath} not found!")

    // Detect and process depending if tasksDefinitions is a file or a directory
    val files: List<File?> = when {
      tasksDefinitions.isFile -> listOf(tasksDefinitions)
      tasksDefinitions.isDirectory -> tasksDefinitions.listFiles(tasksDefinitionsFileExtension)?.asList() ?: emptyList()
      else -> emptyList()
    }

    logger.info("Run config files to be processed: $files")

    // Iterate through files
    files
      .filterNotNull()
      .forEach { file ->
        logger.info("Processing run config file: ${file.absolutePath}")

        YAML()
          .loadAll(file.inputStream())
          .iterator()
          .forEach { raw ->
            val runConfig = raw as IntellijRunConfig
            File(taskDefinitionsOutput, runConfig.filename())
              .apply { writeText(runConfig.toXml()) }
              .also { logger.info("Run config file stored in: ${it.absolutePath}") }
          }
      }
  }
}
