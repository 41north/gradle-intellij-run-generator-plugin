package dev.north.fortyone.gradle.intellij.run.generator.tasks

import dev.north.fortyone.gradle.intellij.run.generator.YAML
import dev.north.fortyone.gradle.intellij.run.generator.models.IntellijRunConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
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

    // Check if output directory is defined
    if (taskDefinitionsOutput == null)
      throw IllegalArgumentException("taskDefinitionsOutput is null!")

    // Ensure output dir exists and create it if necessary
    val outputDir = taskDefinitionsOutput!!.apply { if (!exists()) mkdirs() }

    // Ensure at least we have a definition file or a definition dir
    if (tasksDefinitions == null)
      throw IllegalArgumentException("tasksDefinitionsFile is null! Aborting!")

    // Detect and process depending if tasksDefinitions is a file or a directory
    val files: List<File?>
    when {
      tasksDefinitions!!.isFile -> {

        // If doesn't exist, throw error
        if (!tasksDefinitions!!.exists())
          throw InvalidParameterException("File with path ${tasksDefinitions!!.absolutePath} not found!")

        files = listOf(tasksDefinitions)
      }

      tasksDefinitions!!.isDirectory -> {

        // If doesn't exist, throw error
        if (!tasksDefinitions!!.exists())
          throw InvalidParameterException("Folder with path ${tasksDefinitions!!.absolutePath} not found!")

        // Obtain files
        files = tasksDefinitions!!.listFiles()?.asList() ?: emptyList()
      }

      else -> files = emptyList()
    }

    // Iterate through files
    files
      .forEach {
        // Write definitions
        val stream = it!!.inputStream()
        val definitions = YAML().loadAll(stream).iterator()

        definitions
          .forEach { raw ->
            val definition = raw as IntellijRunConfig
            File(outputDir, definition.filename()).writeText(definition.toXml())
          }
      }
  }
}
