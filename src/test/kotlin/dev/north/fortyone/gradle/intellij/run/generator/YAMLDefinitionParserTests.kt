package dev.north.fortyone.gradle.intellij.run.generator

import dev.north.fortyone.gradle.intellij.run.generator.models.ApplicationRunConfig
import dev.north.fortyone.gradle.intellij.run.generator.models.DockerRunConfig
import dev.north.fortyone.gradle.intellij.run.generator.models.GradleRunConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class YAMLDefinitionParserTests : FunSpec() {

  init {

    test("should process correctly an Application definition config") {
      val stream = javaClass.getResource("/application-config.yaml")!!.openStream()

      val config: ApplicationRunConfig = YAML().load(stream)

      config shouldNotBe null
      with(config) {
        name shouldBe "BESU | Dev | Kafka > Run"
        filename shouldBe "BESU__Dev__Kafka__Run.xml"
        mainClassName shouldBe "org.hyperledger.besu.Besu"
        module shouldBe "exflo.ingestion.kafka.main"
        envs.size shouldBe 11
      }
    }

    test("should process correctly a Docker definition config") {
      val stream = javaClass.getResource("/docker-config.yaml")!!.openStream()

      val config: DockerRunConfig = YAML().load(stream)

      config shouldNotBe null
      with(config) {
        name shouldBe "DOCKER | Kafka"
        filename shouldBe "DOCKER__KAFKA.xml"
        default shouldBe false
        dockerComposeFileName shouldBe "docker-compose.exflo-kafka.yml"
      }
    }

    test("should process correctly a Gradle definition config") {
      val stream = javaClass.getResource("/gradle-config.yaml")!!.openStream()

      val config: GradleRunConfig = YAML().load(stream)

      config shouldNotBe null
      with(config) {
        name shouldBe "GRADLE | Ktlint > Format"
        filename shouldBe "GRADLE__Ktlint__Format.xml"
        default shouldBe false
        tasks.size shouldBe 1
      }
    }

    test("should process multiple definitions configs") {
      val stream = javaClass.getResource("/multiple-definitions.yaml")!!.openStream()

      val configs: Iterable<Any> = YAML().loadAll(stream)

      configs shouldNotBe null
      configs.iterator().forEach { config ->
        config shouldNotBe null
      }
    }
  }

}
