package dev.north.fortyone.gradle.intellij.run.generator.models

import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml

class GradleRunConfig: IntellijRunConfig {

  lateinit var name: String
  lateinit var filename: String
  var default: Boolean = false
  var tasks: List<String> = emptyList()

  override fun toXml(): String =
    xml("component") {
        attribute("name", "ProjectRunConfigurationManager")

        "configuration" {

            attribute("default", default)
            attribute("name", name)
            attribute("type", "GradleRunConfiguration")
            attribute("factoryName", "Gradle")

            "ExternalSystemSettings" {

                "option" {
                    attribute("name", "executionName")
                }

                "option" {
                    attribute("name", "externalProjectPath")
                    attribute("value", "\$PROJECT_DIR\$")
                }

                "option" {
                    attribute("name", "externalSystemIdString")
                    attribute("value", "GRADLE")
                }

                "option" {
                    attribute("name", "scriptParameters")
                    attribute("value", "")
                }

                "option" {
                    attribute("name", "taskDescriptions")

                    "list" {}
                }

                "option" {
                    attribute("name", "taskNames")

                    "list" {

                        for (task in tasks) {
                            "option" {
                                attribute("name", task)
                            }
                        }
                    }
                }
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

            "GradleScriptDebugEnabled" {
                -"true"
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
