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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
  id("org.jlleitschuh.gradle.ktlint-idea") version "9.3.0"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  id("com.github.ben-manes.versions") version "0.29.0"
  id("com.gradle.plugin-publish") version "0.12.0"
}

apply(plugin = "io.spring.dependency-management")
apply(from = "${project.rootDir}/gradle/versions.gradle")

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

group = "dev.north.fortyone.gradle"
version = "0.1.0"

repositories {
  jcenter()
}

gradlePlugin {
  (plugins) {
    register("intellijRunGeneratorPlugin") {
      id = "dev.north.fortyone.intellij.run.generator"
      displayName = "Gradle Intellij Run Generator"
      description = "Generates XML Run configurations files for Intellij with YAML"
      implementationClass = "dev.north.fortyone.gradle.intellij.run.generator.IntellijRunGeneratorPlugin"
    }
  }
}

pluginBundle {
  website = "https://github.com/41north/gradle-intellij-run-generator-plugin"
  vcsUrl = "https://github.com/41north/gradle-intellij-run-generator-plugin"
  tags = listOf("intellij", "generator", "kotlin-dsl")
}

tasks {
  withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
  }

  withType<Test> {
    useJUnitPlatform()
  }
}

dependencies {
  implementation("org.redundent:kotlin-xml-builder")
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
  implementation("javax.xml.bind:jaxb-api")
  implementation("com.sun.xml.bind:jaxb-core")

  testImplementation("io.kotest:kotest-runner-junit5-jvm")
  testImplementation("io.kotest:kotest-runner-console-jvm")
  testImplementation("io.kotest:kotest-assertions-core-jvm")
}
