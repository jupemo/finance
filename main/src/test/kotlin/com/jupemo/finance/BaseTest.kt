package com.jupemo.finance

import com.mongodb.client.MongoClient
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.GenericContainer

@MicronautTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest: TestPropertyProvider {

    @AfterAll
    fun stop() {
        mongoDBContainer.stop()
    }

    private val mongoDBContainer = GenericContainer<Nothing>("mongo:latest").apply {
        withExposedPorts(27017)
        start()
    }

    override fun getProperties(): MutableMap<String, String> {
        return mutableMapOf(
            "mongodb.uri" to "mongodb://${mongoDBContainer.host}:${mongoDBContainer.firstMappedPort}/finance"
        )
    }
}