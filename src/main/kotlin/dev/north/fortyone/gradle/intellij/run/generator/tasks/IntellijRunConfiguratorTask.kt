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
  var tasksDefinitionsFile: File? = null

  @InputDirectory
  var taskDefinitionsDir: File? = null

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
      throw IllegalArgumentException("taskDefinitionsOutput path is null! Aborting!")

    // Ensure output dir exists and create it if necessary
    val outputDir = taskDefinitionsOutput!!.apply { if (!exists()) mkdirs() }

    // Ensure at least we have a definition file or a definition dir
    if (tasksDefinitionsFile == null && taskDefinitionsDir == null)
      throw IllegalArgumentException("TaskDefinitionsFile AND TaskDefinitionsDir are both null! Aborting!")

    // Ensure we comply with requirements
    when {
      tasksDefinitionsFile != null -> {

        // If doesn't exist, throw error
        if (!tasksDefinitionsFile!!.exists())
          throw InvalidParameterException("File with path ${tasksDefinitionsFile!!.absolutePath} not found!")

        // If is not file throw error
        if (!tasksDefinitionsFile!!.isFile)
          throw InvalidParameterException("Task definition file ${tasksDefinitionsFile!!.absolutePath} is not a file!")

        // Proceed to parse
        val isr = tasksDefinitionsFile!!.inputStream()
        val definitions = YAML().loadAll(isr).iterator()

        // Write definitions
        definitions
          .forEach { raw ->
            val definition = raw as IntellijRunConfig
            File(outputDir, definition.filename()).writeText(definition.toXml())
          }
      }

      taskDefinitionsDir != null -> {

        // If doesn't exist, throw error
        if (!taskDefinitionsDir!!.exists())
          throw InvalidParameterException("Folder with path ${taskDefinitionsDir!!.absolutePath} not found!")

        // If is a file throw error
        if (taskDefinitionsDir!!.isFile)
          throw InvalidParameterException("${taskDefinitionsDir!!.absolutePath} is a file instead of a folder!")

        // Obtain files
        val files = taskDefinitionsDir!!.listFiles()

        if (files.isEmpty())
          throw IllegalStateException("${taskDefinitionsDir!!.absolutePath} is empty!")

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
