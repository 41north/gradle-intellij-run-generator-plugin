package dev.north.fortyone.gradle.intellij.run.generator.tasks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dev.north.fortyone.gradle.intellij.run.generator.IntellijRunConfig
import dev.north.fortyone.gradle.intellij.run.generator.TasksDefinitions
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

  private val jackson = ObjectMapper(YAMLFactory()).registerModule(KotlinModule())

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
    val runConfigurationDir = taskDefinitionsOutput.apply { if (!exists()) mkdirs() }

    val td = jackson.readValue(tasksDefinitionsFile, TasksDefinitions::class.java)
      ?: throw InvalidParameterException("Task definition file ${tasksDefinitionsFile.absolutePath} not found or not valid!")

    val configs: List<IntellijRunConfig> = td.application + td.docker + td.gradle
    configs
      .parallelStream()
      .forEach { config ->
        val xml = config.toXml()
        File(runConfigurationDir, config.filename.replace("\\s", "_")).writeText(xml)
      }
  }
}
