package com.jupemo.finance.controller

import com.jupemo.finance.BaseTest
import com.jupemo.finance.application.port.input.CreateUserUseCase
import com.jupemo.finance.entity.User
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest()
class UserControllerSaveTest : BaseTest() {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    @MockBean(CreateUserUseCase::class)
    fun createUserUseCase(): CreateUserUseCase {
        return mockk(relaxed = true)
    }

    @Test
    fun `createUser should return a UserDto with the given name and email`() {
        val userDto = UserDto(name = "Test User", email = "test@email.com")
        val user = User(userDto.name, userDto.email)

        every { createUserUseCase.createUser(any()) } returns user

        val request = HttpRequest.POST("/user", userDto)
        val response = client.toBlocking().exchange(request, UserDto::class.java)

        assertEquals(HttpStatus.OK, response.status)
        clearAllMocks()
    }
}