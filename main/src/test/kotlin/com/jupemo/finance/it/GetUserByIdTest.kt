package com.jupemo.finance.it

import com.jupemo.finance.BaseTest
import com.jupemo.finance.dto.ErrorDto
import com.jupemo.finance.dto.UserDto
import com.jupemo.finance.dto.UserInfoDto
import com.mongodb.client.MongoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

@MicronautTest
class GetUserByIdTest: BaseTest()  {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var mongoClient: MongoClient

    private val userId = ObjectId()

    @BeforeAll
    fun setup() {
        mongoClient.getDatabase("finance").getCollection("user").insertOne(
            org.bson.Document(
                mapOf(
                    "_id" to userId,
                    "name" to "Test User",
                    "email" to "getById@test.com",
                    "version" to 0
                )
            )
        )
    }


    @Test
    fun `getUserByID should return a UserDto with the given id`() {

        val request = HttpRequest.GET<UserDto>("/user/$userId/info")
        val response = client.toBlocking().exchange(request, UserInfoDto::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status())
    }

    @Test
    fun `getUserById should return notFound when the user does not exist`() {
        val badId = ObjectId()

        val request = HttpRequest.GET<UserDto>("/user/$badId/info")
        try {
            client.toBlocking().exchange(request, ErrorDto::class.java)
        } catch (e: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.status)
        }

    }

}