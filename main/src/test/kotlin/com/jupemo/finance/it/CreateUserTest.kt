package com.jupemo.finance.it

import com.jupemo.finance.controller.UserDto
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
class CreateUserTest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun `createUser should return a UserDto with the given name and email`() {
        val userDto = UserDto(name = "Test User", email = "test@email.com")

        val request = HttpRequest.POST("/user", userDto)
        val response = client.toBlocking().exchange(request, UserDto::class.java)

        assertEquals(HttpStatus.OK, response.status())
        assertEquals(userDto.name, response.body().name)
        assertEquals(userDto.email, response.body().email)
        assertNotNull(response.body().id)
    }

}