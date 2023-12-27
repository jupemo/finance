package com.jupemo.finance.it

import com.jupemo.finance.BaseTest
import com.jupemo.finance.dto.AmountDto
import com.jupemo.finance.dto.ErrorDto
import com.mongodb.client.MongoClient
import io.micronaut.http.HttpRequest
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
class BankAccountWithdrawTest : BaseTest() {
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
                            "balance" to "100"
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `withdraw should return a 404 status when user does not exist`() {
        val request = HttpRequest.POST("/user/${ObjectId()}/bank-account/${ObjectId()}/withdraw", AmountDto("100.0"))
        val error =
            assertThrows<HttpClientResponseException> { client.toBlocking().exchange(request, ErrorDto::class.java) }
        assertEquals(404, error.status.code)
    }

    @Test
    fun `withdraw should return 204 status when user exists`() {
        val request = HttpRequest.POST("/user/${userId}/bank-account/${bankId}/withdraw", AmountDto("100.0"))
        val response = client.toBlocking().exchange(request, ErrorDto::class.java)
        assertEquals(204, response.status.code)

        val userDB = mongoClient.getDatabase("finance").getCollection("user").find(Document("_id", userId)).first()
        val db = userDB?.get("bankAccounts") as List<Map<String, Any>>
        assertEquals("0.0", db[0]["balance"])
    }

    @Test
    fun `withdraw should return 400 status when amount is more than balance`() {
        val request = HttpRequest.POST("/user/${userId}/bank-account/${bankId}/withdraw", AmountDto("200.0"))
        val error =
            assertThrows<HttpClientResponseException> { client.toBlocking().exchange(request, ErrorDto::class.java) }

        assertEquals(400, error.status.code)
        assertEquals("Insufficient funds", error.message)
    }


}