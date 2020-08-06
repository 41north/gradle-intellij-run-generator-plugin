package dev.north.fortyone.gradle.intellij.run.generator.tasks

import dev.north.fortyone.gradle.intellij.run.generator.YAML
import dev.north.fortyone.gradle.intellij.run.generator.models.IntellijRunConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.lang.IllegalStateException
import java.security.InvalidParameterException

/**
 * Task that allows to generate XML Run configurations for IntelliJ.
 */
open class IntellijRunConfiguratorTask : DefaultTask() {

  @InputFile
  @InputDirectory
  var tasksDefinitions: File? = null

  @OutputDirectory
  var taskDefinitionsOutput: File? = null

  init {
    group = "Intellij"
    description = "Generates XML Run configurations files for Intellij"
  }

  @TaskAction
  fun run() {

    // Check if is defined
    if (taskDefinitionsOutput == null)
      throw IllegalArgumentException("taskDefinitionsOutput is null! Aborting!")

    // Ensure output dir exists and create it if necessary
    val outputDir = taskDefinitionsOutput!!.apply { if (!exists()) mkdirs() }

    // Ensure at least we have a definition file or a definition dir
    if (tasksDefinitions == null)
      throw IllegalArgumentException("tasksDefinitionsFile is null! Aborting!")

    // Ensure we comply with requirements
    when {
      tasksDefinitions!!.isFile -> {

        // If doesn't exist, throw error
        if (!tasksDefinitions!!.exists())
          throw InvalidParameterException("File with path ${tasksDefinitions!!.absolutePath} not found!")

        // If is not file throw error
        if (!tasksDefinitions!!.isFile)
          throw InvalidParameterException("Task definition file ${tasksDefinitions!!.absolutePath} is not a file!")

        // Proceed to parse
        val isr = tasksDefinitions!!.inputStream()
        val definitions = YAML().loadAll(isr).iterator()

        // Write definitions
        definitions
          .forEach { raw ->
            val definition = raw as IntellijRunConfig
            File(outputDir, definition.filename()).writeText(definition.toXml())
          }
      }

      tasksDefinitions!!.isDirectory -> {

        // If doesn't exist, throw error
        if (!tasksDefinitions!!.exists())
          throw InvalidParameterException("Folder with path ${tasksDefinitions!!.absolutePath} not found!")

        // If is a file throw error
        if (tasksDefinitions!!.isFile)
          throw InvalidParameterException("${tasksDefinitions!!.absolutePath} is a file instead of a folder!")

        // Obtain files
        val files = tasksDefinitions!!.listFiles()

        if (files == null || files.isEmpty())
          throw IllegalStateException("${tasksDefinitions!!.absolutePath} is empty!")

        // Iterate through files
        files
          .forEach {
            val isr = it!!.inputStream()
            val definitions = YAML().loadAll(isr).iterator()

            // Write definitions
            definitions
              .forEach { raw ->
                val definition = raw as IntellijRunConfig
                File(outputDir, definition.filename()).writeText(definition.toXml())
              }
          }
      }
    }
  }
}
