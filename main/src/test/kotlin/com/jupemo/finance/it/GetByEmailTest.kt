package com.jupemo.finance.it

import com.jupemo.finance.BaseTest
import com.jupemo.finance.dto.ErrorDto
import com.jupemo.finance.dto.UserDto
import com.mongodb.client.MongoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.exceptions.HttpException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
class GetByEmailTest : BaseTest() {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var mongoClient: MongoClient

    @BeforeAll
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

    @Test
    fun `getUserByEmail should return notFound when the user does not exist`() {
        val email = "not@found.com"

        val request = HttpRequest.GET<UserDto>("/user/$email")
        try {
            client.toBlocking().exchange(request, ErrorDto::class.java)
        } catch (e: HttpClientResponseException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
        }

    }

}