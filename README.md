# xmc launcher

### An open source multi-lingual Minecraft Launcher based on Kotlin and JavaFX, using Labrinth API.

## Project structure: 
```xmc-launcher
├── api/               # API module for Labrinth (Modrinth API), Auth, and other 3rd-party services
├── application/       # JavaFX UI layer with KDE-style theming
├── buildSrc/          # Shared build configuration, plugins, and dependency versions
├── common/            # Cross-module utilities and JavaFX extensions
├── elevation-service/ # Launcher installer, bootstrap and uninstaller
└── launcher-backend/  # Core logic: translations, themes, settings, and internal services
```

### Note that the Launcher is in the initial stage of development. All credits and links to sources will be listed here.
* **Eroica's FluentLib and theme**: https://github.com/Eroica/javafx-fluent-theme
* **Dukke's DWM utils**: [https://github.com/dukke/FXThemes](https://github.com/dukke/FXThemes/tree/main/FXThemes/src/main/java/impl/com/pixelduke/window/win32)

This project uses [Gradle](https://gradle.org/).
To build and run the application, use the *Gradle* tool window by clicking the Gradle icon in the right-hand toolbar,
or run it directly from the terminal:

* Run `./gradlew run` to build and run the application.
* Run `./gradlew build` to only build the application.
* Run `./gradlew check` to run all checks, including tests.
* Run `./gradlew clean` to clean all build outputs.

Note the usage of the Gradle Wrapper (`./gradlew`).
This is the suggested way to use Gradle in production projects.

[Learn more about the Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

[Learn more about Gradle tasks](https://docs.gradle.org/current/userguide/command_line_interface.html#common_tasks).

This project follows the suggested multi-module setup and consists of the `app` and `utils` subprojects.
The shared build logic was extracted to a convention plugin located in `buildSrc`.

This project uses a version catalog (see `gradle/libs.versions.toml`) to declare and version dependencies
and both a build cache and a configuration cache (see `gradle.properties`).