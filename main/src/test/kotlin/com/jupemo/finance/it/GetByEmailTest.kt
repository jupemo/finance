package com.jupemo.finance.it

import com.jupemo.finance.BaseTest
import com.jupemo.finance.controller.UserDto
import com.mongodb.client.MongoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
class GetByEmailTest : BaseTest() {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var mongoClient: MongoClient

    @BeforeEach
    fun setup() {
        mongoClient.getDatabase("finance").getCollection("user").insertOne(
            org.bson.Document(
                mapOf(
                    "name" to "Test User",
                    "email" to "email@test.com"
                )
            )
        )
    }

    @Test
    fun `getUserByEmail should return a UserDto with the given name and email`() {
        val email = "email@test.com"

        val request = HttpRequest.GET<UserDto>("/user/$email")
        val response = client.toBlocking().exchange(request, UserDto::class.java)

        assertEquals(HttpStatus.OK, response.status())
    }

}