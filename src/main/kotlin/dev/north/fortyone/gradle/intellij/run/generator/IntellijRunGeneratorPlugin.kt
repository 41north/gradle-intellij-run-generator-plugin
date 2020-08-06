/*
 * Copyright (c) 2020 41North.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.north.fortyone.gradle.intellij.run.generator

import dev.north.fortyone.gradle.intellij.run.generator.tasks.IntellijRunConfiguratorTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import java.io.File

@Suppress("UnstableApiUsage", "MemberVisibilityCanBePrivate")
class IntellijRunGeneratorPlugin : Plugin<Project> {

  companion object {
    const val NAME = "intellijRunGenerator"
    const val TASK_NAME = "generateIntellijRunConfigs"
  }

  override fun apply(target: Project) {
    val extension = target.extensions.create<IntellijRunGeneratorExtension>(NAME)

    target.registerTask<IntellijRunConfiguratorTask>(TASK_NAME) {
      tasksDefinitions = extension
        .tasksDefinitions
        .getOrElse(File(target.rootDir, "intellij-run-configs.yaml"))
      taskDefinitionsOutput = extension
        .tasksDefinitionOutput
        .getOrElse(File(target.rootDir, ".idea/runConfigurations"))
    }
  }
}

/**
 * Extension class for configuring [IntellijRunGeneratorPlugin].
 */
interface IntellijRunGeneratorExtension {

  /**
   * Task definition file or directory for generating configs.
   */
  var tasksDefinitions: Property<File>

  /**
   * Output directory where generated configs are stored.
   */
  val tasksDefinitionOutput: Property<File>
}
