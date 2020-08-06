package dev.north.fortyone.gradle.intellij.run.generator.models

import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml

// SnakeYAML prefers to use an empty constructor
class ApplicationRunConfig : IntellijRunConfig {

  lateinit var name: String
  lateinit var filename: String
  lateinit var mainClassName: String
  lateinit var module: String
  var default: Boolean = false
  var programArguments: String = ""
  var envs: Map<String, String> = emptyMap()

  override fun toXml(): String =
    xml("component") {
        attribute("name", "ProjectRunConfigurationManager")

        "configuration" {

            attribute("default", default)
            attribute("name", name)
            attribute("type", "Application")
            attribute("factoryName", "Application")

            "envs" {

                for (entry in envs) {
                    "env" {
                        attribute("name", entry.key)
                        attribute("value", entry.value)
                    }
                }
            }

            "option" {
                attribute("name", "INCLUDE_PROVIDED_SCOPE")
                attribute("value", "true")
            }

            "option" {
                attribute("name", "MAIN_CLASS_NAME")
                attribute("value", mainClassName)
            }

            programArguments?.let {
                "option" {
                    attribute("name", "PROGRAM_PARAMETERS")
                    attribute("value", mainClassName)
                }
            }

            "option" {
                attribute("name", "WORKING_DIRECTORY")
                attribute("value", "\$MODULE_WORKING_DIR\$")
            }

            "module" {
                attribute("name", module)
            }

            "extension" {
                attribute("name", "net.ashald.envfile")

                "option" {
                    attribute("name", "IS_ENABLED")
                    attribute("value", "false")
                }

                "option" {
                    attribute("name", "IS_SUBST")
                    attribute("value", "false")
                }

                "option" {
                    attribute("name", "IS_PATH_MACRO_SUPPORTED")
                    attribute("value", "false")
                }

                "option" {
                    attribute("name", "IS_IGNORE_MISSING_FILES")
                    attribute("value", "false")
                }

                "option" {
                    attribute("name", "IS_ENABLE_EXPERIMENTAL_INTEGRATIONS")
                    attribute("value", "false")
                }

                "ENTRIES" {
                    "ENTRY" {
                        attribute("IS_ENABLED", "true")
                        attribute("PARSER", "runconfig")
                    }
                }
            }

            "method" {
                attribute("v", "2")

                "option" {
                    attribute("name", "Make")
                    attribute("enabled", "true")
                }
            }
        }
    }.toString(PrintOptions(pretty = false, singleLineTextElements = true, useSelfClosingTags = true))
}
