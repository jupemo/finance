pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.8.22"
        id("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
        id("com.google.devtools.ksp") version "1.8.22-1.0.11"
        id("com.github.johnrengelman.shadow") version "8.1.1"
        id("io.micronaut.application") version "4.0.4"
        id("io.micronaut.aot") version "4.0.4"
        id("io.micronaut.library") version "4.0.4"

    }
}


rootProject.name="finance"

include("main")
include("application")
include("infrastructure:rest")
include("infrastructure:persistence")
include("domain")