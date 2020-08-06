package dev.north.fortyone.gradle.intellij.run.generator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

class IntellijRunGeneratorTests : FunSpec() {

  private val projectDir = createTempDir().apply {

    val parent = this

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

    resolve("settings.gradle.kts").apply {
      appendText("rootProject.name = \"gradle-intellij-run-generator-plugin-test\"")
    }

    resolve("build.gradle.kts").apply {
      appendText(
        """
                    plugins {
                      id("dev.north.fortyone.intellij.run.generator")
                    }

                    repositories {
                      mavenCentral()
                    }

                    intellijRunGenerator {
                      tasksDefinitions.set(File("${parent.absolutePath}/intellij-run-configs.yaml"))
                      tasksDefinitionOutputDir.set(File("${parent.absolutePath}/outputs"))
                    }
            """
      )
    }
  }

  init {

    test("IntellijRunConfiguratorTask has been added") {
      val actual = buildResult("tasks")

      actual.output shouldContainIgnoringCase "Intellij tasks"
      actual.assertSuccess(":tasks")
    }

    xtest("IntellijRunConfiguratorTask has been executed") {
      val actual = buildResult("generateIntellijRunConfigs")

      actual.assertSuccess(":generateIntellijRunConfigs")
    }
  }

  private fun BuildResult.assertSuccess(task: String) {
    task(task)?.outcome shouldBe TaskOutcome.SUCCESS
    output.contains("BUILD SUCCESSFUL")
  }

  private fun buildResult(vararg args: String) =
    GradleRunner.create()
      .withProjectDir(projectDir)
      .withArguments(*args)
      .withPluginClasspath()
      .build()
}
