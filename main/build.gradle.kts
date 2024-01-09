plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("io.micronaut.aot")
    id("io.micronaut.test-resources")
}

version = "0.1"
group = "com.jupemo.finance"

val kotlinVersion = project.properties.get("kotlinVersion")

dependencies {
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut.validation:micronaut-validation")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.zalando:logbook-netty:3.7.2")
    implementation("org.zalando:logbook-core:3.7.2")


    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("io.mockk:mockk")
    testImplementation("io.micronaut.mongodb:micronaut-mongo-sync")
    testImplementation("org.testcontainers:testcontainers")

    implementation(project(":infrastructure:persistence"))
    implementation(project(":domain"))
    implementation(project(":application"))
}


application {
    mainClass.set("com.jupemo.finance.ApplicationKt")
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.jupemo.finance.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}



