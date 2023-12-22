plugins {
    id("io.micronaut.library")
}

dependencies {
    implementation(project(":domain"))
}

version = "0.1"
group = "com.jupemo.finance"

micronaut {
    testRuntime("junit5")
}


