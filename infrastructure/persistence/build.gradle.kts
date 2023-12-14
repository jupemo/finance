plugins {
    id("io.micronaut.library")
    id("io.micronaut.test-resources")
}

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")

    implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")

    implementation("io.micronaut.test:micronaut-test-junit5")

    implementation(project(":application"))
    implementation(project(":domain"))
}

micronaut {
    testRuntime("junit5")
}

