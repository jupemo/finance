package com.jupemo.finance.persistence.repository

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
    fun `should save user`() {
        val user = User(name = "name", email = "email")
        // when
        val userSaved = userService.saveUser(user)

        // then
        assertNotNull(userSaved.getId())
        assertEquals("name", userSaved.getName())
    }
}