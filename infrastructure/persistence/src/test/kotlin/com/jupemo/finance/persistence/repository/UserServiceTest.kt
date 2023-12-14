package com.jupemo.finance.persistence.repository

import com.jupemo.finance.entity.BankAccountType
import com.jupemo.finance.entity.User
import com.mongodb.DuplicateKeyException
import com.mongodb.client.MongoClient
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


@MicronautTest
@Property(name = "mongodb.package-names", value = "com.jupemo.finance.persistence.entity")
class UserServiceTest {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var mongoClient: MongoClient

    @Test
    fun `should save user and bank account`() {

        val user = User(name = "name", email = "email")
        user.createBankAccount("name", "currency", BankAccountType.CHECKING, "bank")

        // when
        val userSaved = userService.saveUser(user)

        // then
        assertNotNull(userSaved.id())
        assertEquals("name", userSaved.name())
        assertEquals(1, userSaved.bankAccounts().size)
    }

    @Test
    fun `should save user with no bank account`() {
        val user = User(name = "name", email = "email")

        // when
        val userSaved = userService.saveUser(user)

        // then
        assertNotNull(userSaved.id())
        assertEquals("name", userSaved.name())
        assertEquals(0, userSaved.bankAccounts().size)
    }
    @Test
    fun `should create unique index on email field`() {
        // Arrange
        val database = mongoClient.getDatabase("finance")
        val collection = database.getCollection("user")

        // Act
        val indexInfo = collection.listIndexes().firstOrNull { it.getString("name") == "email_1" }

        // Assert
        assertNotNull(indexInfo)
        assertTrue(indexInfo?.getBoolean("unique") ?: false)
    }
}