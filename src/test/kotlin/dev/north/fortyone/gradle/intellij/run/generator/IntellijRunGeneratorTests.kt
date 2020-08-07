package dev.north.fortyone.gradle.intellij.run.generator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File

class IntellijRunGeneratorTests : FunSpec() {

  init {

    test("this plugin has added its tasks") {

      // Construct project
      val project = createTempDir().apply {

        resolve("settings.gradle.kts").apply {
          appendText("rootProject.name = \"gradle-intellij-run-generator-plugin-test\"")
        }

        resolve("build.gradle.kts").apply {
          appendText(
            """
            |plugins { id("dev.north.fortyone.intellij.run.generator") }
            |
            |repositories { mavenCentral() }
            """.trimMargin()
          )
        }
      }

      // Test
      val actual = buildResult(project, "tasks")

      // Assert
      actual.assertSuccess(":tasks")
      actual.output shouldContainIgnoringCase "Intellij tasks"
    }

    test("IntellijRunConfiguratorTask has been executed with one definition file") {

      // Construct test project
      val project = createTempDir().apply {

        val parent = this

        resolve("settings.gradle.kts").apply {
          appendText("rootProject.name = \"gradle-intellij-run-generator-plugin-test\"")
        }

        resolve("build.gradle.kts").apply {
          appendText(
            """
              |plugins { id("dev.north.fortyone.intellij.run.generator") }
              |
              |repositories { mavenCentral() }
              |
              |intellijRunGenerator {
              |  tasksDefinitions.set(File("${parent.absolutePath}/intellij-run-configs.yaml"))
              |  tasksDefinitionOutput.set(File("${parent.absolutePath}/outputs"))
              |}
            """.trimMargin()
          )
        }

        resolve("intellij-run-configs.yaml").apply {
          appendText(
            """
            |--- !!dev.north.fortyone.gradle.intellij.run.generator.models.ApplicationRunConfig
            |name: Test
            |filename: Test.xml
            |default: false
            |mainClassName: io.test.Test
            |module: io.test.main
            |envs:
            |  BESU_SYNC_MODE: FULL
            |
            """.trimMargin()
          )
        }
      }

      // Test
      val actual = buildResult(project, "generateIntellijRunConfigs")

      // Assert
      actual.assertSuccess(":generateIntellijRunConfigs")
    }

    test("IntellijRunConfiguratorTask has been executed with multiple definition files") {

      // Construct test project
      val project = createTempDir().apply {

        val parent = this

        resolve("config-one.yaml").apply {
          appendText(
            """
            |--- !!dev.north.fortyone.gradle.intellij.run.generator.models.ApplicationRunConfig
            |name: Test
            |filename: one.xml
            |default: false
            |mainClassName: io.test.Test
            |module: io.test.main
            |envs:
            |  BESU_SYNC_MODE: FULL
            |
            """.trimMargin()
          )
        }

        resolve("config-two.yaml").apply {
          appendText(
            """
            |--- !!dev.north.fortyone.gradle.intellij.run.generator.models.ApplicationRunConfig
            |name: Test
            |filename: two.xml
            |default: false
            |mainClassName: io.test.Test
            |module: io.test.main
            |envs:
            |  BESU_SYNC_MODE: FULL
            |
            """.trimMargin()
          )
        }

        resolve("settings.gradle.kts").apply {
          appendText("rootProject.name = \"gradle-intellij-run-generator-plugin-test\"")
        }

        resolve("build.gradle.kts").apply {
          appendText(
            """
              |plugins { id("dev.north.fortyone.intellij.run.generator") }
              |
              |repositories { mavenCentral() }
              |
              |intellijRunGenerator {
              |  tasksDefinitions.set(File("${parent.absolutePath}/"))
              |  tasksDefinitionOutput.set(File("${parent.absolutePath}/outputs"))
              |}
            """.trimMargin()
          )
        }
      }

      // Test
      val actual = buildResult(project, "generateIntellijRunConfigs")

      // Assert
      actual.assertSuccess(":generateIntellijRunConfigs")
    }
  }

  private fun BuildResult.assertSuccess(task: String) {
    task(task)?.outcome shouldBe TaskOutcome.SUCCESS
    output.contains("BUILD SUCCESSFUL")
  }

  private fun buildResult(projectDir: File, vararg args: String) =
    GradleRunner.create()
      .withProjectDir(projectDir)
      .withArguments(*args)
      .withPluginClasspath()
      .withDebug(true)
      .build()
}
