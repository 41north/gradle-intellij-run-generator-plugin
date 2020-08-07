<h1 align="center">✨ Gradle Intellij Run Generator Plugin ✨</h1>

<p align="center">
  <a href="https://github.com/41north/gradle-intellij-run-generator-plugin/workflows/build/badge.svg">
    <img alt="KtLint" height="20px" src="https://github.com/41north/gradle-intellij-run-generator-plugin/workflows/build/badge.svg" />
  </a>
  <a href="https://github.com/41north/gradle-intellij-run-generator-plugin?style=flat-square">
    <img alt="Gradle Plugin License" height="20px" src="https://img.shields.io/github/license/41north/gradle-intellij-run-generator-plugin?style=flat-square" />
  </a>
</p>

<p align="center">
    A <b>Gradle plugin</b> that generates Intellij Run configurations from a YAML definition file, written in <a href="https://kotlinlang.org">Kotlin</a>.
</p>

## 💡 Introduction

This plugin allows to specify [Intellij Run configurations](https://www.jetbrains.com/help/idea/run-debug-configuration.html) with YAML!

For now, the scope of the plugin is quite simplistic, but over time new use cases or improvements may be added!

Originally, this plugin was developed for our Besu plugin: [Exflo](https://github.com/41north/exflo) as a way of sharing complex configurations easily.

## 🙈 Usage

To apply the plugin, use the gradle plugin syntax:

```kotlin
plugins {
  id("dev.north.fortyone.intellij.run.generator") version "0.2.0"
}
```

Once the plugin is applied, you can configure it with the following options (default values are displayed below):

```kotlin
intellijRunGenerator {
  tasksDefinitionsFile.set(File("./intellij-run-configs.yaml"))
  tasksDefinitionsFileExtension = FilenameFilter { _, name -> name.toLowerCase().endsWith(".yaml") || name.toLowerCase().endsWith(".yml") }
  tasksDefinitionOutputDir.set(File(".idea/runConfigurations"))
}
```

Also you can specify a folder instead of a single definition file, just like this (by default it will load all `*.yaml`, `*.yml` files):

```kotlin
intellijRunGenerator {
  tasksDefinitionsFile.set(File("./intellij-run-configs/"))
}
```

If you want to see how the definition file looks like, there're some examples inside [`samples/intellij-run-configs.yaml`](samples/intellij-run-configs.yaml).

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
