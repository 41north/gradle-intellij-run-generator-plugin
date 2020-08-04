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

plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  `maven-publish`
  id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
  id("org.jlleitschuh.gradle.ktlint-idea") version "9.2.1"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  id("com.github.ben-manes.versions") version "0.28.0"
  id("com.gradle.plugin-publish") version "0.12.0"
}

apply(plugin = "io.spring.dependency-management")
apply(from = "${project.rootDir}/gradle/versions.gradle")

group = "dev.north.fortyone.gradle"
version = "0.1.0"

repositories {
  mavenLocal()
  jcenter()
}

gradlePlugin {
  (plugins) {
    register("flatbuffersPlugin") {
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

dependencies {
  gradleApi()

  implementation("org.redundent:kotlin-xml-builder")
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")

  implementation("javax.xml.bind:jaxb-api:2.3.0")
  implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
}

publishing {
  repositories {
    maven(url = "build/repository")
  }
}
