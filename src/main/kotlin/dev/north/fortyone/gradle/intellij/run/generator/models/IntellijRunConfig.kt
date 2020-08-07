package dev.north.fortyone.gradle.intellij.run.generator.models

interface IntellijRunConfig {
  fun filename(): String
  fun toXml(): String
}
