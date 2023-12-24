package com.jupemo.finance.it

import com.jupemo.finance.BaseTest
import com.jupemo.finance.dto.AmountDto
import com.jupemo.finance.dto.ErrorDto
import com.mongodb.client.MongoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.bson.Document
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest
class BankAccountDepositTest : BaseTest() {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var mongoClient: MongoClient

    private val userId = ObjectId()
    private val bankId = ObjectId()

    @BeforeAll
    fun setup() {
        mongoClient.getDatabase("finance").getCollection("user").insertOne(
            org.bson.Document(
                mapOf(
                    "_id" to userId,
                    "name" to "Test User",
                    "email" to "bankAccountDeposit@test.com",
                    "version" to 0,
                    "bankAccounts" to listOf(
                        mapOf(
                            "id" to bankId.toString(),
                            "name" to "Test Account",
                            "currency" to "USD",
                            "type" to "CHECKING",
                            "bank" to "Test Bank",
                            "balance" to "0"
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `deposit should return a 404 status when user does not exist`() {
        val request = HttpRequest.POST("/user/${ObjectId()}/bank-account/${ObjectId()}/deposit", AmountDto("100.0"))
        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, ErrorDto::class.java)
        }
        assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }

    @Test
    fun `deposit should return a 204 when OK`() {
        val request = HttpRequest.POST("/user/${userId}/bank-account/${bankId}/deposit", AmountDto("100.0"))
        val response = client.toBlocking().exchange(request, AmountDto::class.java)
        assertEquals(HttpStatus.NO_CONTENT, response.status)
        val userDB = mongoClient.getDatabase("finance").getCollection("user").find(Document("_id", userId)).first()
        val db = userDB?.get("bankAccounts") as List<Map<String, Any>>
        assertEquals("100.0", db[0]["balance"])

    }
}