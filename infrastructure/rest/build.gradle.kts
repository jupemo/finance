plugins {
    id("io.micronaut.library")
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")

    implementation(project(":application"))
}