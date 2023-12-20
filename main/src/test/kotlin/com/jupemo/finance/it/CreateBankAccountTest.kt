package com.jupemo.finance.it

import com.jupemo.finance.BaseTest
import com.jupemo.finance.dto.BankAccountDto
import com.jupemo.finance.dto.ErrorDto
import com.jupemo.finance.entity.BankAccountType
import com.mongodb.client.MongoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

@MicronautTest
class CreateBankAccountTest : BaseTest() {

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
                    "email" to "bakAccount@test.com",
                    "version" to 0
                )
            )
        )
    }


    @Test
    fun `createBankAccount should return a BankAccountDto with the given details`() {
        val bankAccountDto =
            BankAccountDto(name = "Test Account", currency = "USD", type = BankAccountType.CHECKING, bank = "Test Bank")


        val request = HttpRequest.POST("/user/${userId}/bank-account", bankAccountDto)
        val response = client.toBlocking().exchange(request, BankAccountDto::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }

    @Test
    fun `createBankAccount should return a 404 status when user does not exist`() {
        val bankAccountDto =
            BankAccountDto(name = "Test Account", currency = "USD", type = BankAccountType.CHECKING, bank = "Test Bank")
        val nonExistingUserId = ObjectId().toString()

        val request = HttpRequest.POST("/user/$nonExistingUserId/bank-account", bankAccountDto)
        try {
            client.toBlocking().exchange(request, ErrorDto::class.java)
        } catch (e: HttpClientResponseException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
        }
    }


}