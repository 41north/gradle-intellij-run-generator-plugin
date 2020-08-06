package dev.north.fortyone.gradle.intellij.run.generator.models

import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml

class DockerRunConfig: IntellijRunConfig {

  lateinit var name: String
  lateinit var filename: String
  var default: Boolean = false
  var dockerComposeFileName: String = ""

  override fun toXml(): String =
    xml("component") {
        attribute("name", "ProjectRunConfigurationManager")

        "configuration" {

            attribute("default", default)
            attribute("name", name)
            attribute("type", "docker-deploy")
            attribute("factoryName", "docker-compose.yml")
            attribute("server-name", "Docker")

            "deployment" {
                attribute("type", "docker-compose.yml")

                "settings" {

                    "option" {
                        attribute("name", "sourceFilePath")
                        attribute("value", dockerComposeFileName)
                    }
                }
            }

            "method" {
                attribute("v", "2")
            }
        }
    }.toString(PrintOptions(pretty = false, singleLineTextElements = true, useSelfClosingTags = true))
}
