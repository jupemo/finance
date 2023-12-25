plugins {
    id("io.micronaut.library")
}

dependencies {
    implementation(project(":domain"))
    implementation("io.mockk:mockk")
}

version = "0.1"
group = "com.jupemo.finance"

micronaut {
    testRuntime("junit5")
}


