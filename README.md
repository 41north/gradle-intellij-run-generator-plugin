<h1 align="center">✨ Gradle Intellij Run Generator Plugin ✨</h1>

<p align="center">
  <a href="https://github.com/41north/gradle-intellij-run-generator-plugin/workflows/KtLint/badge.svg">
    <img alt="KtLint" height="20px" src="https://github.com/41north/gradle-intellij-run-generator-plugin/workflows/KtLint/badge.svg" />
  </a>
  <a href="https://github.com/41north/gradle-intellij-run-generator-plugin?style=flat-square">
    <img alt="Gradle Plugin License" height="20px" src="https://img.shields.io/github/license/41north/gradle-intellij-run-generator-plugin?style=flat-square" />
  </a>
</p>

<p align="center">
    A <b>Gradle plugin</b> that generates Intellij Run configurations from a YAML definition file, written in <a href="https://kotlinlang.org">Kotlin</a>.
</p>

## 💡 Introduction

This plugin was developed originally for [Exflo](https://github.com/41north/exflo).

## 🙈 Usage

To apply the plugin, use the gradle plugin syntax:

```kotlin
plugins {
  id("dev.north.fortyone.intellij.run.generator") version "0.1.0"
}
```

Once the plugin is applied, you can configure it with the following options (default values are displayed below):

```kotlin
`intellij-run-generator` {
  tasksDefinitionsFile.set(File("./intellij-run-configs.yaml"))
  tasksDefinitionOutputDir.set(File(".idea/runConfigurations"))
}
```

If you want to see how the definition file looks like, there's one example taken from Exflo inside [`samples/intellij-run-configs.yaml`](samples/intellij-run-configs.yaml).

## 💻 Contribute

We welcome any kind of contribution or support to this project but before to do so:

* Make sure you have read the [contribution guide](/.github/CONTRIBUTING.md) for more details on how to submit a good PR (pull request).

Also, we are not only limited to technical contributions. Things that make us happy are:

* Add a [GitHub Star](https://github.com/41north/gradle-intellij-run-generator-plugin/stargazers) to the project.
* Tweet about this project.
* Write a review or tutorial.

## Other Gradle plugins

We have published other gradle plugins:

- [Solidity Gradle Plugin](https://github.com/41north/solidity-gradle-plugin).
- [Gradle FlatBuffers Plugin](https://github.com/41north/gradle-flatbuffers-plugin).

## 📬 Get in touch

`Gradle Intellij Run Generator Plugin` has been developed initially by [°41North](https://41north.dev). 

If you think this project would be useful for your use case and want to talk more about it you can reach out to us via our contact form or by sending an email to `hello@41north.dev`. We try to respond within 48 hours and look forward to hearing from you.

## ✍️ License

`Gradle Intellij Run Generator Plugin` is free and open-source software licensed under the [Apache 2.0 License](./LICENSE).
