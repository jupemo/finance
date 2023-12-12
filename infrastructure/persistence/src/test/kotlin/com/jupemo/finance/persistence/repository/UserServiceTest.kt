package com.jupemo.finance.persistence.repository

import com.jupemo.finance.entity.BankAccountType
import com.jupemo.finance.entity.User
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


@MicronautTest
@Property(name = "mongodb.package-names", value = "com.jupemo.finance.persistence.entity")
class UserServiceTest {

    @Inject
    lateinit var userService: UserService

    @Test
    fun `should save user and bank account`() {

        val user = User(name = "name", email = "email")
        user.createBankAccount("name", "currency", BankAccountType.CHECKING, "bank")

        // when
        val userSaved = userService.saveUser(user)

        // then
        assertNotNull(userSaved.getId())
        assertEquals("name", userSaved.getName())
        assertEquals(1, userSaved.getBankAccounts().size)
    }

    @Test
    fun `should save user with no bank account`() {
        val user = User(name = "name", email = "email")

        // when
        val userSaved = userService.saveUser(user)

        // then
        assertNotNull(userSaved.getId())
        assertEquals("name", userSaved.getName())
        assertEquals(0, userSaved.getBankAccounts().size)
    }
}