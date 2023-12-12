package com.jupemo.finance.controller

import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.entity.User
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
class UserControllerTest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient


    private val createUserUseCase: CreateUserUseCase = mockk<CreateUserUseCase>()

    @Test
    fun `createUser should return a UserDto with the given name and email`() {
        val userDto = UserDto("Test User", "test@email.com")
        every { createUserUseCase.createUser(any()) } returns User(userDto.name.toString(), userDto.email.toString())

        val request = HttpRequest.POST("/user", userDto)
        val response = client.toBlocking().exchange(request, UserDto::class.java)

        assertEquals(HttpStatus.OK, response.status())
        assertEquals(userDto, response.body())
    }
}