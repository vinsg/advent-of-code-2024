pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        // add repositories:
        google()
        maven("https://packages.jetbrains.team/maven/p/amper/amper")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
}

plugins {
    // apply the plugin:
    id("org.jetbrains.amper.settings.plugin").version("0.5.0")
}

rootProject.name = "advent-of-code-2024"